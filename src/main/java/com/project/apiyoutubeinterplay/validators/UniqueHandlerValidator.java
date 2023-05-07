package com.project.apiyoutubeinterplay.validators;

import com.project.apiyoutubeinterplay.repositories.ChannelRepository;
import com.project.apiyoutubeinterplay.services.ChannelService;
import com.project.apiyoutubeinterplay.validators.annotation.UniqueHandler;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueHandlerValidator implements ConstraintValidator<UniqueHandler, String> {
    private final ChannelService channelService;

    public UniqueHandlerValidator(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    public boolean isValid(String handler, ConstraintValidatorContext context) {
        if(handler == null) return true;
        return channelService.isValidChannelByHandler(handler);
    }
}