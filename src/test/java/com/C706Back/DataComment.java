package com.C706Back;


import com.C706Back.models.builder.CommentResponseBuilder;
import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.entity.Pet;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

public class DataComment {

    public static Optional<CommentResponse> createComment001() {
        Pet pet = new Pet();
        pet.setId(1L);

        Date date = new GregorianCalendar(2022, Calendar.SEPTEMBER, 30).getTime();

        CommentResponseBuilder commentResponseBuilder = new CommentResponseBuilder();
        return Optional.of(commentResponseBuilder.id(1L)
                .message("Mensaje")
                .createdDate(date)
                .updatedDate(date)
                .pet(pet)
                .user(null)
                .build());
    }

    public static Optional<CommentResponse> createComment002() {
        Pet pet = new Pet();
        pet.setId(1L);

        Date date = new GregorianCalendar(2022, Calendar.SEPTEMBER, 30).getTime();

        CommentResponseBuilder commentResponseBuilder = new CommentResponseBuilder();
        return Optional.of(commentResponseBuilder.id(2L)
                .message("Mensaje2")
                .createdDate(date)
                .updatedDate(date)
                .pet(pet)
                .user(null)
                .build());
    }
}
