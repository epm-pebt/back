package com.epam.ecobites.service;

import com.epam.ecobites.domain.converter.ReviewConvertToDTO;
import com.epam.ecobites.domain.converter.ReviewConvertToEntity;
import com.epam.ecobites.data.ReviewRepository;
import com.epam.ecobites.domain.Review;
import com.epam.ecobites.domain.dto.ReviewDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements CrudService<ReviewDTO, Long> {
    private ReviewRepository reviewRepository;
    private ReviewConvertToDTO convertToDTO;
    private ReviewConvertToEntity convertToEntity;

    public ReviewService(ReviewRepository reviewRepository, ReviewConvertToDTO convertToDTO, ReviewConvertToEntity convertToEntity) {
        this.reviewRepository = reviewRepository;
        this.convertToDTO = convertToDTO;
        this.convertToEntity = convertToEntity;
    }

    @Override
    public ReviewDTO create(ReviewDTO reviewDto) {
        nullCheck(reviewDto);
        Review review = convertToEntity.convert(reviewDto);
        Review savedReview = reviewRepository.save(review);
        return convertToDTO.convert(savedReview);
    }

    @Override
    public ReviewDTO getById(Long id) {
        nullCheck(id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
        return convertToDTO.convert(review);
    }

    @Override
    public List<ReviewDTO> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(convertToDTO::convert).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO update(Long id, ReviewDTO updatedReviewDto) {
        nullCheck(id);
        nullCheck(updatedReviewDto);
        validateIds(id, updatedReviewDto);
        Review existingReview = findReviewById(id);
        Review updatedReview = convertToEntity.convert(updatedReviewDto);
        updateReviewFields(existingReview, updatedReview);
        Review savedReview = reviewRepository.save(existingReview);
        return convertToDTO.convert(savedReview);
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
        nullCheck(id);
        if (!reviewRepository.existsById(id)) {
            throw new NotFoundException("Review with id " + id + " not found");
        }
        reviewRepository.deleteById(id);
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }
}
