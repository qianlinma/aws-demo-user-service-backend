package com.mycompany.user.demo.grpc;

import com.mycompany.grpc.user.GetUserProfileRequest;
import com.mycompany.grpc.user.UserProfileResponse;
import com.mycompany.grpc.user.UserServiceGrpc;
import com.mycompany.user.demo.model.UserProfile;
import com.mycompany.user.demo.service.UserService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;

    public UserGrpcService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void getUserProfile(
            GetUserProfileRequest request,
            StreamObserver<UserProfileResponse> responseObserver
    ) {
        UserProfile userProfile = userService.getUserProfile(request.getUserId());

        UserProfileResponse response = UserProfileResponse.newBuilder()
                .setId(userProfile.id())
                .setName(userProfile.name())
                .setMembershipLevel(userProfile.membershipLevel())
                .setRegion(userProfile.region())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
