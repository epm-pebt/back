package com.epam.ecobites.service;

import com.epam.ecobites.converter.ReviewConverter;
import com.epam.ecobites.data.ReviewRepository;
import com.epam.ecobites.domain.Review;
import com.epam.ecobites.dto.ReviewDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements CrudService<ReviewDTO, Long> {
    private ReviewRepository reviewRepository;
    private ReviewConverter reviewConverter;

    public ReviewService(ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
    public ReviewDTO create(ReviewDTO reviewDto) {
        Review review = reviewConverter.convertToEntity(reviewDto);
        Review savedReview = reviewRepository.save(review);
        return reviewConverter.convertToDTO(savedReview);
    }

    @Override
    public ReviewDTO get(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
        return reviewConverter.convertToDTO(review);
    }

    @Override
    public List<ReviewDTO> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(reviewConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO update(Long id, ReviewDTO updatedReviewDto) {
        validateIds(id, updatedReviewDto);
        Review existingReview = findReviewById(id);
        Review updatedReview = reviewConverter.convertToEntity(updatedReviewDto);
        updateReviewFields(existingReview, updatedReview);
        Review savedReview = reviewRepository.save(existingReview);
        return reviewConverter.convertToDTO(savedReview);
    }

    private void validateIds(Long id, ReviewDTO updatedReviewDto) {
        if (!id.equals(updatedReviewDto.getId())) {
            throw new IllegalArgumentException("ID in the path and ID in the Review object must be the same");
        }
    }

    private Review findReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
    }

    private void updateReviewFields(Review existingReview, Review updatedReview) {
        existingReview.setRate(updatedReview.getRate());
        existingReview.setContent(updatedReview.getContent());
        existingReview.setDateCreated(updatedReview.getDateCreated());
        existingReview.setEcoUser(updatedReview.getEcoUser());
        existingReview.setRecipe(updatedReview.getRecipe());
    }

    @Override
    public void delete(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new NotFoundException("Review with id " + id + " not found");
        }
        reviewRepository.deleteById(id);
    }
}
