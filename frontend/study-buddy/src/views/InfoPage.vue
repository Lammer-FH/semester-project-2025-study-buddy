<template>
  <base-layout page-title="Info">
    <div class="icon-school ion-text-center">
      <ion-icon
        :icon="school"
        fill="outline"
        color="primary"
        size="large"
        style="width: 4em; height: 4em"
      ></ion-icon>
    </div>
    <ion-card class="ion-margin">
      <ion-card-header>
        <ion-card-title>Study Buddy</ion-card-title>
        <ion-card-subtitle>Your personal study assistant</ion-card-subtitle>
      </ion-card-header>

      <ion-card-content>
        <p>Study Buddy helps students to:</p>
        <ion-list lines="none">
          <ion-item
            ><ion-label>📚 Manage university courses</ion-label></ion-item
          >
          <ion-item
            ><ion-label
              >✅ Track assignments and completion</ion-label
            ></ion-item
          >
          <ion-item
            ><ion-label>🗂️ Organize tasks by course</ion-label></ion-item
          >
          <ion-item><ion-label>⏰ Stay ahead of deadlines</ion-label></ion-item>
        </ion-list>
      </ion-card-content>
    </ion-card>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import BaseLayout from "@/components/template/BaseLayout.vue";
import {
  IonIcon,
  IonItem,
  IonCardContent,
  IonList,
  IonCard,
  IonCardHeader,
  IonLabel,
  IonCardTitle,
  IonCardSubtitle,
} from "@ionic/vue";
import { school } from "ionicons/icons";
export default defineComponent({
  name: "InfoPage",
  components: {
    BaseLayout,
    IonIcon,
    IonItem,
    IonCardContent,
    IonList,
    IonCard,
    IonCardHeader,
    IonLabel,
    IonCardTitle,
    IonCardSubtitle,
  },
  data() {
    return { school };
  },
});
</script>

<style scoped>
.icon-school {
  color: #000;
}
</style>
