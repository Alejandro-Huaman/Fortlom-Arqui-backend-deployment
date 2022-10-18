package com.fortlom.content.application.service;
import com.fortlom.content.application.exception.ResourceNotFoundException;
import com.fortlom.content.application.exception.ResourcePerzonalized;
import com.fortlom.content.domain.ContentAgrregate.entity.Event;
import com.fortlom.content.domain.ContentAgrregate.repository.EventRepository;
import com.fortlom.content.domain.ContentAgrregate.service.EventService;
import com.fortlom.content.domain.ContentAgrregate.valueobject.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private static final String ENTITY = "Event";
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Event> getAllEvents() {
        List<Event>events=eventRepository.findAll();
        for (Event event:events){
            Artist artist= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/"+event.getArtistid(),Artist.class);
            event.setArtist(artist);
        }
        return events;


    }

    @Override
    public Page<Event> getAllEvents(Pageable pageable) {

        Page<Event>events=eventRepository.findAll(pageable);
        for (Event event:events){
             Artist artist= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/"+event.getArtistid(),Artist.class);
             event.setArtist(artist);
        }

        return events;
    }

    @Override
    public Event getEventById(Long eventId) {
        Event event=eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, eventId));
        Artist artist= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/"+event.getArtistid(),Artist.class);
        event.setArtist(artist);
        return event;
    }

    @Override
    public Event createEvent(Long Artist, Event request) {
        boolean check= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/"+Artist,boolean.class);
        boolean check2= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/checkpremium/"+Artist,boolean.class);
        if(check && check2){
            request.setArtistid(Artist);
            Date date = new Date();
            request.setRegisterdate(date);


            return eventRepository.save(request);
        }else{
            throw  new ResourcePerzonalized("id inexistente");
        }
    }

    @Override
    public Event updateEventreleasedate(Long eventId, Date releasedDate) {
        return eventRepository.findById(eventId).map(post->{
            post.setReleasedDate(releasedDate);
            eventRepository.save(post);
            return  post;
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, eventId));
    }

    @Override
    public List<Event> getEventsByArtistId(Long artistId) {
        boolean check= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/check/"+artistId,boolean.class);
        if(check){
            List<Event>events=eventRepository.findByArtistid(artistId);
            for (Event event:events){
                Artist artist= restTemplate.getForObject("https://fortlom-account.herokuapp.com/api/v1/userservice/artists/"+event.getArtistid(),Artist.class);
                event.setArtist(artist);
            }

            return events;

        }
        else {
            throw  new ResourcePerzonalized("id inexistente");

        }
    }

    @Override
    public ResponseEntity<?> deleteEvent(Long eventId) {
        return eventRepository.findById(eventId).map(event -> {
            eventRepository.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, eventId));
    }

    @Override
    public boolean existsById(Long eventId) {
        return eventRepository.existsById(eventId);
    }
}
