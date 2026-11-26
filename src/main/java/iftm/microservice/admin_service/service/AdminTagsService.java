package iftm.microservice.admin_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iftm.microservice.admin_service.domain.AdminTags;
import iftm.microservice.admin_service.dto.AdminTagsDTO;
import iftm.microservice.admin_service.repository.AdminTagsRepository;
import iftm.microservice.admin_service.utils.StatusTag;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminTagsService {

    private final AdminTagsRepository adminTagsRepository;

    @Transactional(readOnly = true)
    public List<AdminTagsDTO> listarTags() {
        return adminTagsRepository.findAll()
            .stream()
            .map(AdminTagsDTO::new)
            .toList();
    }

    @Transactional(readOnly = true)
    public AdminTagsDTO buscarPorCodigo(String uuid) {
        AdminTags tag = adminTagsRepository.findByTagUuid(uuid)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido - tag não encontrada."));

        return new AdminTagsDTO(tag);
    }

    @Transactional
    public AdminTagsDTO criar(AdminTagsDTO dto) {
        if (adminTagsRepository.existsByNameAndCategoria(dto.getNome(), dto.getCategoria())) {
            throw new IllegalArgumentException("Tag já existente.");
        }

        AdminTags novaTag = new AdminTags();
        novaTag.setTagUuid(UUID.randomUUID().toString());
        novaTag.setNome(dto.getNome());
        novaTag.setCategoria(dto.getCategoria());
        novaTag.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusTag.ATIVA);

        AdminTags novaTagSalva = adminTagsRepository.save(novaTag);
        return new AdminTagsDTO(novaTagSalva);
    }

    @Transactional
    public AdminTagsDTO atualizar(String tagUuid, AdminTagsDTO dto) {
        AdminTags tagExistente = adminTagsRepository.findByTagUuid(tagUuid)
            .orElseThrow(() -> new IllegalArgumentException("Tag não encontrada."));

        tagExistente.setNome(dto.getNome());
        tagExistente.setCategoria(dto.getCategoria());
        if (dto.getStatus() != null) {
            tagExistente.setStatus(dto.getStatus());
        }

        AdminTags tagAtualizada = adminTagsRepository.save(tagExistente);
        return new AdminTagsDTO(tagAtualizada);
    }

    @Transactional
    public void deletar(String tagUuid) {
        AdminTags tag = adminTagsRepository.findByTagUuid(tagUuid)
            .orElseThrow(() -> new IllegalArgumentException("Tag não encontrada."));

        adminTagsRepository.delete(tag);
    }
}