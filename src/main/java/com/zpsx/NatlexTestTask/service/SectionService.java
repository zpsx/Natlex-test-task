package com.zpsx.NatlexTestTask.service;

import com.zpsx.NatlexTestTask.domain.GeoClass;
import com.zpsx.NatlexTestTask.domain.Section;
import com.zpsx.NatlexTestTask.dto.SectionRequestBody;
import com.zpsx.NatlexTestTask.repository.GeoClassRepo;
import com.zpsx.NatlexTestTask.repository.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {

    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    GeoClassRepo geoClassRepo;

    public List<Section> readSectionsByGeoCode(String code){
        return sectionRepo.findAllByGeoCode(code);
    }

    public Section createSection(SectionRequestBody sectionRequestBody){
        List<GeoClass> geoClasses = new ArrayList<>();
        for(String code: sectionRequestBody.getGeoCodes()){
            GeoClass geoClass = geoClassRepo.findById(code)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            String.format("Geological class with code \"%s\" does not exit.", code)));
            geoClasses.add(geoClass);
        }

        Section section = new Section(sectionRequestBody.getName(), geoClasses);
        sectionRepo.save(section);

        return section;
    }

    public Section readSection(long id){
        return sectionRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Section with id \"%s\" does not exit.", id)));
    }

    public Section updateSection(@RequestBody SectionRequestBody sectionRequestBody){
        Section section = sectionRepo.findById(sectionRequestBody.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Section with id \"%s\" does not exit.", sectionRequestBody.getId())));

        List<GeoClass> geoClasses = new ArrayList<>();
        for(String code: sectionRequestBody.getGeoCodes()){
            GeoClass geoClass = geoClassRepo.findById(code)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            String.format("Geological class with code \"%s\" does not exit.", code)));

            geoClasses.add(geoClass);
        }

        section.setName(sectionRequestBody.getName());
        section.setGeoClasses(geoClasses);

        sectionRepo.save(section);

        return section;
    }

    public Section deleteSection(long id){
        Section section = sectionRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Section with id \"%s\" does not exit.", id)));
        sectionRepo.delete(section);

        return section;
    }
}