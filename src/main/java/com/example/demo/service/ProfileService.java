package com.example.demo.service;

import com.example.demo.dao.ProfileRepository;
import com.example.demo.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

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
            oldProfile.setLocation(newProfileData.getLocation());
            oldProfile.setStatus(newProfileData.getStatus());
            oldProfile.setUser(newProfileData.getUser());
            oldProfile.setUsername(newProfileData.getUsername());
            return profileRepository.save(oldProfile);
        }else{
            return null;
        }
    }

    public void deleteProfileById(Long id) {
        profileRepository.deleteById(id);
    }
}
