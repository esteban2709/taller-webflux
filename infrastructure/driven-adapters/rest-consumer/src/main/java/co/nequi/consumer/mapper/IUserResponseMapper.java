package co.nequi.consumer.mapper;

import co.nequi.consumer.dto.response.UserResponseDto;
import co.nequi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserResponseMapper {

    @Mapping(target = "firstName", source = "first_name")
    @Mapping(target = "lastName", source = "last_name")
    User toModel(UserResponseDto userResponseDto);
}
