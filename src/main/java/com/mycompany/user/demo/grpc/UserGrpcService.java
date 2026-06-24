package com.mycompany.user.demo.grpc;

import com.mycompany.grpc.user.GetUserProfileRequest;
import com.mycompany.grpc.user.UserProfileResponse;
import com.mycompany.grpc.user.UserServiceGrpc;
import com.mycompany.user.demo.model.UserProfile;
import com.mycompany.user.demo.service.UserService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(UserGrpcService.class);

    private final UserService userService;

    public UserGrpcService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void getUserProfile(
            GetUserProfileRequest request,
            StreamObserver<UserProfileResponse> responseObserver
    ) {
        try {
            UserProfile userProfile = userService.getUserProfile(request.getUserId());

            UserProfileResponse response = UserProfileResponse.newBuilder()
                    .setId(userProfile.id())
                    .setName(userProfile.name())
                    .setMembershipLevel(userProfile.membershipLevel())
                    .setRegion(userProfile.region())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (RuntimeException e) {
            logger.warn("User gRPC request failed for userId={}", request.getUserId(), e);
            responseObserver.onError(Status.UNAVAILABLE
                    .withDescription("User service unavailable")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
