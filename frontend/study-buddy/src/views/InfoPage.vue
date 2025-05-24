<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Info</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Study Buddy</ion-title>
        </ion-toolbar>
      </ion-header>
      <h5>Your personal study assistent</h5>
      <p>Studdy buddy helps millions of students manage university courses, track assignments, organize tasks, and meet deadlines. 
        The app lets you create and organize assignments per course and track completion.</p>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/vue';
</script>
