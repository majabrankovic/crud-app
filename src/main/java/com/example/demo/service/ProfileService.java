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

        Profile oldProfile = null;
        Optional<Profile> optionalProfile = profileRepository.findById(profile.getId());
        if (optionalProfile.isPresent()) {
            oldProfile = optionalProfile.get();
            oldProfile.setLocation(profile.getLocation());
            oldProfile.setStatus(profile.getStatus());
            oldProfile.setUser(profile.getUser());
            oldProfile.setUsername(profile.getUsername());
            profileRepository.save(oldProfile);
        }else{
            return new Profile();
        }

        return oldProfile;
    }

    public void deleteProfileById(Long id) {
        profileRepository.deleteById(id);
    }
}
