package iftm.microservice.admin_service.dto;

import iftm.microservice.admin_service.domain.AdminMentorTags; 
import jakarta.validation.constraints.NotNull;

public class AdminMentorTagsDTO {

    @NotNull(message = "O ID do mentor é obrigatório.")
    private Long mentorId;

    @NotNull(message = "O ID da tag é obrigatório.")
    private Long tagId;

    private String mentor;
    private String tag;

    public AdminMentorTagsDTO() {
    }

    public AdminMentorTagsDTO(AdminMentorTags adminMentorTags) {
        this.mentorId = adminMentorTags.getMentor().getId();
        this.tagId = adminMentorTags.getTag().getId();
        this.mentor = adminMentorTags.getMentor().getName();
        this.tag = adminMentorTags.getTag().getName();
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}