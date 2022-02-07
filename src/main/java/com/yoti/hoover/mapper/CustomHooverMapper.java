package com.yoti.hoover.mapper;

import com.yoti.hoover.controller.dto.CleaningInstructionsDto;
import com.yoti.hoover.controller.dto.CleaningResultDto;
import com.yoti.hoover.model.CleaningInstructionsModel;
import com.yoti.hoover.model.CleaningResultModel;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.annotation.Configuration;

/**
 * CustomHooverMapper. Configures MapperFacade for object mapping
 *
 * @author Roman_Haida
 * 31/01/2022
 */
@Configuration
public class CustomHooverMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(CleaningInstructionsDto.class, CleaningInstructionsModel.class)
                .mapNulls(true)
                .byDefault()
                .customize(new CleaningInstructionsDtoToModelMapper())
                .register();

        factory.classMap(CleaningResultDto.class, CleaningResultModel.class)
                .mapNulls(true)
                .byDefault()
                .register();
    }

    private static class CleaningInstructionsDtoToModelMapper extends CustomMapper<CleaningInstructionsDto, CleaningInstructionsModel> {
        @Override
        public void mapAtoB(CleaningInstructionsDto dto, CleaningInstructionsModel model, MappingContext context) {
            super.mapAtoB(dto, model, context);
            model.setPatches(dto.getPatches());
        }
    }
}
