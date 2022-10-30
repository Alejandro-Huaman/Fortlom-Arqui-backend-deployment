package com.fortlom.administration.interfaces.mapping.entity;
import com.fortlom.administration.domain.adminAgreegate.entity.Admin;
import com.fortlom.administration.interfaces.dto.Admin.AdminResource;
import com.fortlom.administration.interfaces.dto.Admin.CreateAdminResource;
import com.fortlom.administration.interfaces.mapping.configuration.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class AdminMapper {

    @Autowired
    EnhancedModelMapper mapper;



    public AdminResource toResource(Admin model) {
        return mapper.map(model, AdminResource.class);
    }
    public AdminResource toResource(Optional<Admin> model) {
        return mapper.map(model, AdminResource.class);
    }

    public Page<AdminResource> modelListToPage(List<Admin> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, AdminResource.class), pageable, modelList.size());
    }
    public Admin toModel(CreateAdminResource resource) {
        return mapper.map(resource, Admin.class);
    }
}
