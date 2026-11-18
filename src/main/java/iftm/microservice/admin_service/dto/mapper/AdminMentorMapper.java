package iftm.microservice.admin_service.dto.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import iftm.microservice.admin_service.domain.AdminMentores;
import iftm.microservice.admin_service.domain.AdminTags;
import iftm.microservice.admin_service.dto.AdminMentoresDTO;

@Mapper(componentModel = "spring")
public interface AdminMentorMapper {

    @Mapping(target = "usuario", ignore = true)
    AdminMentores toEntity(AdminMentoresDTO adminMentoresDTO);

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "nome", source = "usuario.nome")
    AdminMentoresDTO toDto(AdminMentores adminMentores);

    List<AdminMentores> toEntityList(List<AdminMentoresDTO> adminMentoresDto);

    List<AdminMentoresDTO> toDtoList(List<AdminMentores> adminMentores);

    default List<String> mapTags(Set<AdminTags> tags) {
        if (tags == null) {
            return List.of();
        }
        return tags.stream()
                .map(AdminTags::getName)
                .sorted()
                .toList();
    }
}