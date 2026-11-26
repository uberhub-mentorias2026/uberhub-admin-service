package iftm.microservice.admin_service.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import iftm.microservice.admin_service.domain.AdminMentorTags;
import iftm.microservice.admin_service.domain.AdminMentores;
import iftm.microservice.admin_service.dto.AdminMentoresDTO;

@Mapper(componentModel = "spring")
public interface AdminMentorMapper {

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "mentorTags", ignore = true)
    AdminMentores toEntity(AdminMentoresDTO adminMentoresDTO);

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "nome", source = "usuario.nome")
    @Mapping(target = "tags", source = "mentorTags")
    AdminMentoresDTO toDto(AdminMentores adminMentores);

    List<AdminMentores> toEntityList(List<AdminMentoresDTO> adminMentoresDto);

    List<AdminMentoresDTO> toDtoList(List<AdminMentores> adminMentores);

    default List<String> mapMentorTags(List<AdminMentorTags> mentorTags) {
        if (mentorTags == null) {
            return List.of();
        }
        return mentorTags.stream()
                .map(mt -> mt.getTag().getName()) 
                .sorted()
                .toList();
    }
}