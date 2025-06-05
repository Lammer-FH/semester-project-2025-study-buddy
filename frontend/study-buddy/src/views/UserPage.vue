<template>
  <base-layout page-title="Account Information">
    <p v-if="loading">Lade Daten ...</p>
    <div v-else>
      <ion-list>
        <ion-item>
          <ion-label position="stacked">Name</ion-label>
          <ion-text>{{ currentUser.name }}</ion-text>
        </ion-item>

        <ion-item>
          <ion-label position="stacked">E-Mail</ion-label>
          <ion-text>{{ currentUser.email }}</ion-text>
        </ion-item>

        <ion-item>
          <ion-label position="stacked">Studiengang</ion-label>
          <ion-text>{{ currentUser.program }}</ion-text>
        </ion-item>

        <ion-item>
          <ion-label position="stacked">Semester</ion-label>
          <ion-text>{{ currentUser.semester }}</ion-text>
        </ion-item>

        <ion-item>
          <ion-label position="stacked">Matrikelnummer</ion-label>
          <ion-text>{{ currentUser.studentNumber }}</ion-text>
        </ion-item>
      </ion-list>
    </div>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useUserStore } from "@/stores/userStore";
import { IonItem, IonLabel, IonText, IonList } from "@ionic/vue";

export default defineComponent({
  name: "UserPage",
  components: {
    IonItem,
    IonLabel,
    IonText,
    IonList,
  },
  data() {
    return {
      userStore: useUserStore(),
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
  mounted() {
    this.userStore.getUser(1); //Id hardcoded because we don't have authentication
    console.log("loading");
  },
});
</script>
