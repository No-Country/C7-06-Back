package com.C706Back.util;

import com.C706Back.models.enums.Role;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.UnsupportedEncodingException;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class JwtUtils {

    private ScriptEngine engine;
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    public JwtUtils(){
        ScriptEngineManager manager = new ScriptEngineManager();
        final String language = "ECMAScript";
        engine = manager.getEngineByName(language);

    }

    public boolean verify(String authorization) throws Exception {
        boolean response = true;

        if (authorization.startsWith("Bearer ")){
            String token = authorization.substring(7, authorization.length());

            String[] chunks = token.split("\\.");
            SignatureAlgorithm sa = HS256;
            SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(), sa.getJcaName());
            String tokenWithoutSignature = chunks[0] + "." + chunks[1];
            String signature = chunks[2];

            DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);

            if (!validator.isValid(tokenWithoutSignature, signature)) {
                response = false;
            }

        } else {
            response = false;
            System.out.println("Bearer token not found");
        }

        return response;
    }

    public Role getRole(String authorization) throws UnsupportedEncodingException, ScriptException {
        String payload = getPayLoad(authorization);
        engine.eval("var obj = JSON.parse('" + payload + "');");
        engine.eval("var role = obj.role");
        String role = engine.get("role").toString();
        System.out.println("Role: " + role);

        if(role.equals("admin"))
            return Role.admin;

        if(role.equals("user"))
            return Role.user;

        return null;
    }

    public Long getUserId(String authorization) throws UnsupportedEncodingException, ScriptException {
        String payload = getPayLoad(authorization);
        engine.eval("var obj = JSON.parse('" + payload + "');");
        engine.eval("var userId = obj.userId");
        return Long.parseLong(engine.get("userId").toString());
    }

    public String getPayLoad(String authorization) throws UnsupportedEncodingException {
        String payload = authorization.split("\\.")[1];
        return new String(Base64.decodeBase64(payload), "UTF-8");
    }
}
