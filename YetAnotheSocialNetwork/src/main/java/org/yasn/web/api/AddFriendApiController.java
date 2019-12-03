package org.yasn.web.api;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddFriendApiController {

    @PostMapping("/api/add-friend")
    public void sendFriendRequest(@ModelAttribute(name = "profileId") String profileId) {

    }
}
