package com.C706Back.mapper;

public abstract class ManualMapper<D, E> {

    abstract D mapEntityToDTO(E entity);

    abstract E mapDTOtoEntity(D dto);
}
