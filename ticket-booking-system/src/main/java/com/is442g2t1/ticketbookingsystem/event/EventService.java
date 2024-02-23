package com.is442g2t1.ticketbookingsystem.event;

import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import com.is442g2t1.response.StatusResponse;
import com.is442g2t1.response.SuccessResponse;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    @Transactional
    public ResponseEntity<?> createEvent(EventCreateDTO eventCreateDTO) {
        try {
            // Validation
            // empty fields
            if (eventCreateDTO.getEventTitle() == null || eventCreateDTO.getEventTitle().isBlank() ||
                    eventCreateDTO.getEventDate() == null || eventCreateDTO.getEventDate().toString().isBlank() ||
                    eventCreateDTO.getEventDesc() == null || eventCreateDTO.getEventDesc().isBlank() ||
                    eventCreateDTO.getEventLoc() == null || eventCreateDTO.getEventLoc().isBlank() ||
                    eventCreateDTO.getStartTime() == null || eventCreateDTO.getStartTime().isBlank() ||
                    eventCreateDTO.getEndTime() == null || eventCreateDTO.getEndTime().isBlank() ||
                    eventCreateDTO.getCapacity() == null) {
                StatusResponse statusResponse = new StatusResponse("Fields cannot be empty", HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
            }

            // invalid capacity (capacity must be greater than 0)
            if (eventCreateDTO.getCapacity() <= 0) {
                StatusResponse invalidCapacityResponse = new StatusResponse("Capacity must be greater than 0",
                        HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(invalidCapacityResponse);
            }

            // Mappping dto to entity
            Event event = eventMapper.mapToEvent(eventCreateDTO);

            // filled
            event.setFilled(0);

            Event savedEvent = eventRepository.save(event);

            Integer eventId = savedEvent.getEventId();

        } catch (Exception e) {

            e.printStackTrace();
            StatusResponse statusResponse = new StatusResponse("Error creating event : " + e.getMessage(),
                    HttpStatus.SC_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);

        }
    }

    public ResponseEntity<?> editEvent(EventCreateDTO eventCreateDTO, Integer eventId) {
        try {

            // Find existing event to update
            Optional<Event> optionalEvent = eventRepository.findById(eventId);

            // Event not found
            if (optionalEvent.isEmpty()) {
                StatusResponse notFoundResponse = new StatusResponse("Event not found", HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(notFoundResponse);
            }

            Event existingEvent = optionalEvent.get();

            // Validation
            // empty fields
            if (eventCreateDTO.getEventTitle() == null || eventCreateDTO.getEventTitle().isBlank() ||
                    eventCreateDTO.getEventDate() == null || eventCreateDTO.getEventDate().toString().isBlank() ||
                    eventCreateDTO.getEventDesc() == null || eventCreateDTO.getEventDesc().isBlank() ||
                    eventCreateDTO.getEventLoc() == null || eventCreateDTO.getEventLoc().isBlank() ||
                    eventCreateDTO.getStartTime() == null || eventCreateDTO.getStartTime().isBlank() ||
                    eventCreateDTO.getEndTime() == null || eventCreateDTO.getEndTime().isBlank() ||
                    eventCreateDTO.getCapacity() == null) {
                // rewardPoints can be 0, this is dependent on the admin
                StatusResponse statusResponse = new StatusResponse("Fields cannot be empty", HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
            }

            // invalid capacity (capacity must be greater than 0)
            if (eventCreateDTO.getCapacity() <= 0) {
                StatusResponse invalidCapacityResponse = new StatusResponse("Capacity must be greater than 0",
                        HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(invalidCapacityResponse);
            }

            // Mappping dto to entity
            Event event = eventMapper.mapToEvent(eventCreateDTO);

            // eventId (take from existing event)
            event.setEventId(existingEvent.getEventId());

            // filled (take from existing event)
            event.setFilled(existingEvent.getFilled());

            eventRepository.save(event);

            SuccessResponse successResponse = new SuccessResponse("Event edited successfully", HttpStatus.SC_OK, event);
            return ResponseEntity.ok().body(successResponse);

        } catch (Exception e) {

            e.printStackTrace();
            StatusResponse statusResponse = new StatusResponse("Error editing event : " + e.getMessage(),
                    HttpStatus.SC_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);

        }
    }

    public ResponseEntity<?> searchById(Integer eventId) {
        try {
            Event event = eventRepository.searchById(eventId);
            if (event != null) {
                SuccessResponse successResponse = new SuccessResponse("Success", HttpStatus.SC_OK, event);
                return ResponseEntity.ok().body(successResponse);
            } else {
                StatusResponse statusResponse = new StatusResponse("Event with id does not exist",
                        HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            StatusResponse statusResponse = new StatusResponse("Error getting by id" + e.getMessage(),
                    HttpStatus.SC_BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
        }
    }

    public ResponseEntity<?> searchByTitle(String eventTitle) {
        try {
            Event event = eventRepository.searchByTitle(eventTitle);
            if (event != null) {
                SuccessResponse successResponse = new SuccessResponse("Success", HttpStatus.SC_OK, event);
                return ResponseEntity.ok().body(successResponse);
            } else {
                StatusResponse statusResponse = new StatusResponse("Event with title does not exist",
                        HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);
            }
        } catch (Exception e) {
            StatusResponse statusResponse = new StatusResponse("Error getting event by title",
                    HttpStatus.SC_BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
        }
    }

    public ResponseEntity<?> searchByLocation(String eventLoc) {
        try {
            Event event = eventRepository.searchByLocation(eventLoc);
            if (event != null) {
                SuccessResponse successResponse = new SuccessResponse("Success", HttpStatus.SC_OK, event);
                return ResponseEntity.ok().body(successResponse);
            } else {
                StatusResponse statusResponse = new StatusResponse("Event with location does not exist",
                        HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);
            }
        } catch (Exception e) {
            StatusResponse statusResponse = new StatusResponse("Error getting event by location",
                    HttpStatus.SC_BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
        }
    }

    public ResponseEntity<?> deleteById(int eventId) {
        try {
            // Check if the event exists
            Optional<Event> optionalEvent = eventRepository.findById(eventId);
            if (optionalEvent.isEmpty()) {
                // Handle event not found
                StatusResponse notFoundResponse = new StatusResponse("Event not found", HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(notFoundResponse);
            }

            // Delete the event from the database
            eventRepository.deleteById(eventId);

            SuccessResponse successResponse = new SuccessResponse("Event deleted successfully", HttpStatus.SC_OK,
                    eventId);
            return ResponseEntity.ok().body(successResponse);

        } catch (Exception e) {
            e.printStackTrace();
            StatusResponse statusResponse = new StatusResponse("Error deleting event: " + e.getMessage(),
                    HttpStatus.SC_INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(statusResponse);
        }
    }
}
