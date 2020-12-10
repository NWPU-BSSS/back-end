package com.nwpu.bsss.response;

import java.util.ArrayList;

public class UserSubscribesAndFansResponse {
    private ArrayList<SubscribeAndFansBody> userSubscribesAndFans ;

    public UserSubscribesAndFansResponse(ArrayList<SubscribeAndFansBody> userSubscribes) {
        this.userSubscribesAndFans = userSubscribes;
    }
    public UserSubscribesAndFansResponse() {
        this.userSubscribesAndFans = new ArrayList<SubscribeAndFansBody>();
    }

    public ArrayList<SubscribeAndFansBody> getUserSubscribesAndFans() {
        return userSubscribesAndFans;
    }

    public void setUserSubscribes(ArrayList<SubscribeAndFansBody> userSubscribes) {
        this.userSubscribesAndFans = userSubscribes;
    }


    public static class SubscribeAndFansBody{
        private String avatar;
        private String nickname;
        private String introduction;
        private Long bloggerId;
        private boolean isSubscribed;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public Long getBloggerId() {
            return bloggerId;
        }

        public void setBloggerId(Long bloggerId) {
            this.bloggerId = bloggerId;
        }

        public boolean getIsSubscribed() {
            return isSubscribed;
        }

        public void setIsSubscribed(boolean isSubscribed) {
            this.isSubscribed = isSubscribed;
        }
    }
}
