package com.epam.ecobites.service;

import com.epam.ecobites.domain.converter.ReviewConvertToDTO;
import com.epam.ecobites.domain.converter.ReviewConvertToEntity;
import com.epam.ecobites.data.ReviewRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.Review;
import com.epam.ecobites.domain.dto.ReviewDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewConvertToDTO convertToDTO;

    @Mock
    private ReviewConvertToEntity convertToEntity;

    @InjectMocks
    private ReviewService reviewService;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String CONTENT = "test";
    private static final String UPDATED_CONTENT = "testOther";

    @DisplayName("Test creating a review with valid data")
    @Test
    public void testCreateReview() {
        ReviewDTO reviewDTO = createReviewDTO(ID, CONTENT);
        Review review = createReview(ID, CONTENT);

        when(convertToEntity.convert(reviewDTO)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        when(convertToDTO.convert(review)).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.create(reviewDTO);

        assertEquals(reviewDTO, result);
        verify(reviewRepository).save(review);
    }

    @DisplayName("Test getting a review by id when the review exists")
    @Test
    public void testGetReview_Successful() {
        Review review = createReview(ID, CONTENT);
        ReviewDTO reviewDTO = createReviewDTO(ID, CONTENT);

        when(reviewRepository.findById(ID)).thenReturn(Optional.of(review));
        when(convertToDTO.convert(review)).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.getById(ID);

        assertEquals(reviewDTO, result);
    }

    @DisplayName("Test getting a review by id when the review does not exist")
    @Test
    public void testGetReview_Failed() {
        when(reviewRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.getById(ID));
    }

    @DisplayName("Test getting all reviews")
    @Test
    public void testGetAllReviews() {
        Review review1 = createReview(ID, CONTENT);
        Review review2 = createReview(2L, CONTENT);
        List<Review> reviewList = Arrays.asList(review1, review2);

        ReviewDTO reviewDTO1 = createReviewDTO(ID, CONTENT);
        ReviewDTO reviewDTO2 = createReviewDTO(2L, CONTENT);
        List<ReviewDTO> reviewDTOList = Arrays.asList(reviewDTO1, reviewDTO2);

        when(reviewRepository.findAll()).thenReturn(reviewList);
        when(convertToDTO.convert(review1)).thenReturn(reviewDTO1);
        when(convertToDTO.convert(review2)).thenReturn(reviewDTO2);

        List<ReviewDTO> result = reviewService.getAll();

        assertEquals(reviewDTOList, result);
    }

    @DisplayName("Test updating a review when the review exists")
    @Test
    public void testUpdateReview_Successful() {
        ReviewDTO updatedReviewDTO = createReviewDTO(ID, UPDATED_CONTENT);
        Review existingReview = createReview(ID, CONTENT);
        Review updatedReview = createReview(ID, UPDATED_CONTENT);

        when(reviewRepository.findById(ID)).thenReturn(Optional.of(existingReview));
        when(convertToEntity.convert(updatedReviewDTO)).thenReturn(updatedReview);
        when(reviewRepository.save(existingReview)).thenReturn(updatedReview);
        when(convertToDTO.convert(updatedReview)).thenReturn(updatedReviewDTO);

        ReviewDTO result = reviewService.update(ID, updatedReviewDTO);

        assertEquals(updatedReviewDTO, result);
        verify(reviewRepository).save(existingReview);
    }

    @DisplayName("Test updating a review when the review does not exist")
    @Test
    public void testUpdateReview_Failed() {
        ReviewDTO reviewDTO = createReviewDTO(ID, CONTENT);

        when(reviewRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.update(ID, reviewDTO));
    }

    @DisplayName("Test deleting a review when the review exists")
    @Test
    public void testDeleteReview_Successful() {
        when(reviewRepository.existsById(ID)).thenReturn(true);

        reviewService.delete(ID);

        verify(reviewRepository).deleteById(ID);
    }

    @DisplayName("Test deleting a review when the review does not exist")
    @Test
    public void testDeleteReview_Failed() {
        when(reviewRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> reviewService.delete(ID));
    }

    @DisplayName("Test creating a review with null data")
    @Test
    public void testCreateReview_NullReviewDTO() {
        assertThrows(IllegalArgumentException.class, () -> reviewService.create(null));
    }

    @DisplayName("Test updating a review with null data")
    @Test
    public void testUpdateReview_NullReviewDTO() {
        assertThrows(IllegalArgumentException.class, () -> reviewService.update(ID, null));
    }

    @DisplayName("Test updating a review with null id")
    @Test
    public void testUpdateReview_NullId() {
        ReviewDTO reviewDTO = createReviewDTO(ID, CONTENT);
        assertThrows(IllegalArgumentException.class, () -> reviewService.update(null, reviewDTO));
    }

    @DisplayName("Test getting a review with null id")
    @Test
    public void testGetReview_NullId() {
        assertThrows(IllegalArgumentException.class, () -> reviewService.getById(null));
    }

    @DisplayName("Test deleting a review with null id")
    @Test
    public void testDeleteReview_NullId() {
        assertThrows(IllegalArgumentException.class, () -> reviewService.delete(null));
    }

    @DisplayName("Test deleting a review with an invalid id")
    @Test
    public void testDeleteReviewWithInvalidId() {
        assertThrows(NotFoundException.class, () -> reviewService.delete(INVALID_ID));
    }

    private Review createReview(Long id, String content) {
        Review review = new Review();
        review.setId(id);
        review.setRate(1);
        review.setContent(content);
        review.setDateCreated(new Date());
        review.setEcoUser(new EcoUser());
        review.setRecipe(new Recipe());
        return review;
    }

    private ReviewDTO createReviewDTO(Long id, String content) {
        return ReviewDTO.builder()
                .id(id)
                .rate(1)
                .content(content)
                .dateCreated(new Date())
                .ecoUserId(id)
                .recipeId(id)
                .build();
    }
}
