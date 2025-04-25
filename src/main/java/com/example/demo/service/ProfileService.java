package com.example.demo.service;

import com.example.demo.dao.ProfileRepository;
import com.example.demo.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElse(new Profile());
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile updateProfile(Profile profile) {

        return updateOldProfile(profile);
    }

    private Profile updateOldProfile(Profile newProfileData){
        Optional<Profile> optionalProfile = profileRepository.findById(newProfileData.getId());
        if (optionalProfile.isPresent()) {
            Profile oldProfile = optionalProfile.get();

             Profile updated = oldProfile.toBuilder()
            .location(newProfileData.getLocation())
            .status(newProfileData.getStatus())
            .user(newProfileData.getUser())
            .username(newProfileData.getUsername()).build();

            return profileRepository.save(updated);
        }else{
            return Profile.builder().build();
        }
    }

    public void deleteProfileById(Long id) {
        profileRepository.deleteById(id);
    }
}
