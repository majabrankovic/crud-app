package com.example.demo.service;

import com.example.demo.dao.ProfileRepository;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    private Profile profile;

    @BeforeEach
    void setUp() {
        profile = Profile.builder()
                .id(1L)
                .username("maja123")
                .status("active")
                .location("Zemun")
                .build();
    }

    @Test
    public void ProfileService_CreateProfile_ReturnProfile() {
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = profileService.createProfile(profile);

        Assertions.assertThat(result).isEqualTo(profile);
        verify(profileRepository).save(profile);
    }

    @Test
    public void ProfileService_GetProfileById_ReturnProfileWhenProfileExists() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        Profile result = profileService.getProfileById(1L);

        Assertions.assertThat(result).isEqualTo(profile);
    }

    @Test
    public void ProfileService_GetProfileById_ReturnEmptyProfileWhenProfileDoesNotExist() {
        when(profileRepository.findById(99L)).thenReturn(Optional.empty());

        Profile result = profileService.getProfileById(99L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void ProfileService_GetAllProfiles_ReturnProfiles() {
        List<Profile> profileList = List.of(profile);
        when(profileRepository.findAll()).thenReturn(profileList);

        List<Profile> result = profileService.getAllProfiles();

        Assertions.assertThat(result).isEqualTo(profileList);
    }

    @Test
    public void ProfileService_UpdateProfile_ReturnProfileWhenProfileExists() {
        when(profileRepository.findById(profile.getId())).thenReturn(Optional.of(profile));
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        Profile result = profileService.updateProfile(profile);

        Assertions.assertThat(result.getUsername()).isEqualTo(profile.getUsername());
    }

    @Test
    public void ProfileService_UpdateProfile_ReturnEmptyProfileWhenProfileDoesNotExist() {
        when(profileRepository.findById(profile.getId())).thenReturn(Optional.empty());

        Profile result = profileService.updateProfile(profile);

        Assertions.assertThat(result.getId()).isNull();
    }

    @Test
    public void ProfileService_DeleteProfileById_ReturnProfileDeleted() {
        Long profileId = 1L;

        profileService.deleteProfileById(profileId);

        verify(profileRepository, times(1)).deleteById(profileId);
    }

}
