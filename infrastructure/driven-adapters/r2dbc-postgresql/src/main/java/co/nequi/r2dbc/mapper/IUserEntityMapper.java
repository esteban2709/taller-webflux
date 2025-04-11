package co.nequi.r2dbc.mapper;

import co.nequi.model.user.User;
import co.nequi.r2dbc.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserEntityMapper {

    UserEntity toUserEntity(User user);
    User toUser(UserEntity userEntity);
}
