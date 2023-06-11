package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.ReviewDto;
import bg.sofia.uni.fmi.web.project.mapper.ReviewMapper;
import bg.sofia.uni.fmi.web.project.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@Validated
@Mapper
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper mapper;

    @PostMapping(params = {"assigned_vendor_id"})
    public long addGuest(@Valid @NotNull(message = "The guestDto cannot be null!") @RequestBody ReviewDto reviewDto,
                         @Valid
                         @RequestParam("assigned_vendor_id")
                         @NotNull(message = "The vendor id cannot be null!")
                         @Positive(message = "The vendor id must be above 0!")
                         long vendorId) {
        return reviewService.addReview(mapper.toEntity(reviewDto), vendorId);
    }

    @GetMapping
    public List<ReviewDto> getAllVendors() {
        return mapper.toDtoCollection(reviewService.getAllReviews());
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<ReviewDto> findById(@Valid
                                              @Positive(message = "ReviewID must be positive")
                                              @RequestParam("id") long id) {
        return ResponseEntity.ok(mapper.toDto(reviewService.getReviewById(id)));
    }

    @GetMapping(value = "/search", params = {"comment"})
    public ResponseEntity<ReviewDto> findByComment(@Valid
                                                   @RequestParam("comment")
                                                   @NotNull(message = "The comment cannot be null!")
                                                   @NotEmpty(message = "The comment cannot be empty!")
                                                   @NotBlank(message = "The comment cannot be blank!")
                                                   String comment) {
        return ResponseEntity.ok(mapper.toDto(reviewService.getReviewByComment(comment)));
    }

    @GetMapping(value = "/search", params = {"rating"})
    public ResponseEntity<List<ReviewDto>> findByRating(@Valid
                                                        @RequestParam("rating")
                                                        @NotNull(message = "The given rating cannot be null!")
                                                        @Positive(message = "The given rating must be above 0!")
                                                        BigDecimal rating) {
        return ResponseEntity.ok(mapper.toDtoCollection(reviewService.getReviewsByRating(rating)));
    }

    @GetMapping(value = "/search", params = {"photo_link"})
    public ResponseEntity<List<ReviewDto>> findByPhotoLink(@Valid
                                                           @RequestParam("photo_link")
                                                           @NotNull(message = "The photo link cannot be null!")
                                                           @NotEmpty(message = "The photo link cannot be empty!")
                                                           @NotBlank(message = "The photo link cannot be blank!")
                                                           String photoLink) {
        return ResponseEntity.ok(mapper.toDtoCollection(reviewService.getReviewsByPhotoLink(photoLink)));
    }

    @GetMapping(value = "/search", params = {"assigned_vendor_id"})
    public ResponseEntity<List<ReviewDto>> findByPhotoLink(@Valid
                                                           @RequestParam("assigned_vendor_id")
                                                           @Positive(message = "The given id must be above 0!")
                                                           long vendorId) {
        return ResponseEntity.ok(mapper.toDtoCollection(reviewService.getReviewsByAssociatedVendorId(vendorId)));
    }

    @DeleteMapping(value = "/delete", params = {"deleted", "id"})
    public boolean deleteGuest(@RequestParam("deleted")
                               boolean deleted,
                               @Positive(message = "The given ID cannot be less than zero!")
                               @RequestParam("id")
                               long reviewId) {
        return reviewService.delete(deleted, reviewId);
    }
}