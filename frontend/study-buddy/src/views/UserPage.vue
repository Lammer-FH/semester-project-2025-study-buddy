<template>
  <base-layout page-title="My Profile">
    <app-spinner v-if="loading"></app-spinner>
    <div v-else class="profile-container">
      <!-- Avatar Section -->
      <div class="avatar-section">
        <ion-avatar class="profile-avatar">
          <ion-icon
            :icon="personCircle"
            fill="outline"
            color="primary"
          ></ion-icon>
        </ion-avatar>
        <h2 class="profile-name">{{ currentUser.name }}</h2>
      </div>

      <!-- Profile Details -->
      <ion-list lines="none" class="profile-details">
        <ion-item class="detail-item">
          <ion-icon :icon="mailOutline" slot="start" color="medium"></ion-icon>
          <ion-label>E-Mail</ion-label>
          <ion-text class="detail-value">{{ currentUser.email }}</ion-text>
        </ion-item>

        <ion-item class="detail-item">
          <ion-icon
            :icon="schoolOutline"
            slot="start"
            color="medium"
          ></ion-icon>
          <ion-label>Degree Program</ion-label>
          <ion-text class="detail-value">{{ currentUser.program }}</ion-text>
        </ion-item>

        <ion-item class="detail-item">
          <ion-icon
            :icon="calendarNumber"
            slot="start"
            color="medium"
          ></ion-icon>
          <ion-label>Semester</ion-label>
          <ion-text class="detail-value">{{ currentUser.semester }}</ion-text>
        </ion-item>

        <ion-item class="detail-item">
          <ion-icon :icon="idCard" slot="start" color="medium"></ion-icon>
          <ion-label>Matriculation Number</ion-label>
          <ion-text class="detail-value">{{
            currentUser.studentNumber
          }}</ion-text>
        </ion-item>
      </ion-list>
    </div>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useUserStore } from "@/stores/userStore";
import {
  IonItem,
  IonLabel,
  IonText,
  IonList,
  IonAvatar,
  IonIcon,
} from "@ionic/vue";
import {
  personCircle,
  mailOutline,
  schoolOutline,
  calendarNumber,
  idCard,
  createOutline,
} from "ionicons/icons";
import AppSpinner from "@/components/atoms/AppSpinner.vue";

export default defineComponent({
  name: "UserPage",
  components: {
    IonItem,
    IonLabel,
    IonText,
    IonList,
    IonAvatar,
    IonIcon,
    AppSpinner,
  },
  data() {
    return {
      userStore: useUserStore(),
      // Icons
      personCircle,
      mailOutline,
      schoolOutline,
      calendarNumber,
      idCard,
      createOutline,
    };
  },
  computed: {
    currentUser() {
      return this.userStore.currentUser;
    },
    loading() {
      return this.userStore.isLoading;
    },
  },
  methods: {
    handleEdit() {
      // Add your edit logic here
      console.log("Edit profile clicked");
    },
  },
  mounted() {
    this.userStore.getUser(1); //Id hardcoded because we don't have authentication
  },
});
</script>

<style scoped>
.profile-container {
  padding: 16px;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px 0 30px;
}

.profile-avatar {
  width: 100px;
  height: 100px;
  margin-bottom: 16px;
  background: var(--ion-color-light);
}

.profile-avatar ion-icon {
  width: 100%;
  height: 100%;
}

.profile-name {
  margin: 0;
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--ion-color-dark);
}

.profile-details {
  background: transparent;
  margin-bottom: 20px;
}

.detail-item {
  --padding-start: 0;
  --inner-padding-end: 0;
  --background: transparent;
  margin-bottom: 16px;
}

.detail-item ion-label {
  font-size: 0.9rem;
  color: var(--ion-color-medium);
  margin-bottom: 4px;
}

.detail-value {
  font-size: 1rem;
  color: var(--ion-color-dark);
  display: block;
  margin-top: 4px;
}

.action-buttons {
  margin-top: auto;
  padding: 0 16px 20px;
}

/* Add some animation */
ion-avatar {
  transition: transform 0.3s ease;
}

ion-avatar:active {
  transform: scale(0.95);
}

/* Responsive adjustments */
@media (min-width: 768px) {
  .profile-container {
    max-width: 600px;
    margin: 0 auto;
  }

  .profile-avatar {
    width: 120px;
    height: 120px;
  }
}
</style>
