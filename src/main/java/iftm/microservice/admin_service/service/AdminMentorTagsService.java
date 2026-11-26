package iftm.microservice.admin_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iftm.microservice.admin_service.domain.AdminMentorTagId;
import iftm.microservice.admin_service.domain.AdminMentorTags;
import iftm.microservice.admin_service.domain.AdminMentores;
import iftm.microservice.admin_service.domain.AdminTags;
import iftm.microservice.admin_service.dto.AdminMentorTagsDTO;
import iftm.microservice.admin_service.repository.AdminMentorTagsRepository;
import iftm.microservice.admin_service.repository.AdminMentoresRepository;
import iftm.microservice.admin_service.repository.AdminTagsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMentorTagsService {

    private final AdminMentorTagsRepository mentorTagsRepository;
    private final AdminMentoresRepository mentoresRepository;
    private final AdminTagsRepository tagsRepository;

    @Transactional(readOnly = true)
    public List<AdminMentorTagsDTO> listarPorMentor(Long mentorId) {
        return mentorTagsRepository.findByMentorId(mentorId)
                .stream()
                .map(AdminMentorTagsDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AdminMentorTagsDTO> listarPorTag(Long tagId) {
        return mentorTagsRepository.findByTagId(tagId)
                .stream()
                .map(AdminMentorTagsDTO::new)
                .toList();
    }

    @Transactional
    public AdminMentorTagsDTO vincular(AdminMentorTagsDTO dto) {
        if (mentorTagsRepository.existsByMentorIdAndTagId(dto.getMentorId(), dto.getTagId())) {
            throw new IllegalArgumentException("Este mentor já possui esta tag vinculada.");
        }

        AdminMentores mentor = mentoresRepository.findById(dto.getMentorId())
                .orElseThrow(() -> new IllegalArgumentException("Mentor não encontrado."));

        AdminTags tag = tagsRepository.findById(dto.getTagId())
                .orElseThrow(() -> new IllegalArgumentException("Tag não encontrada."));

        AdminMentorTags vinculo = new AdminMentorTags(mentor, tag);
        AdminMentorTags salvo = mentorTagsRepository.save(vinculo);

        return new AdminMentorTagsDTO(salvo);
    }

    @Transactional
    public void desvincular(Long mentorId, Long tagId) {
        AdminMentorTagId id = new AdminMentorTagId(mentorId, tagId);

        AdminMentorTags vinculo = mentorTagsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vínculo entre mentor e tag não encontrado."));

        mentorTagsRepository.delete(vinculo);
    }
    
}