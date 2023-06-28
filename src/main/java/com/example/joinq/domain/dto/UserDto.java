package com.example.joinq.domain.dto;

import com.example.joinq.domain.entities.Organization;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

    private String username;
    private String email;
    private String contactNumber;
    private boolean isOrganizationAccount;
    private Organization organization;

}
