package org.iesvdm.mapper;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.modelo.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    //ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);


    public ClienteDTO clienteAClienteDTO(Cliente cliente, Integer numeroTotalPedidos);

    public Cliente clienteDTOACliente(ClienteDTO clienteDTO);

}
