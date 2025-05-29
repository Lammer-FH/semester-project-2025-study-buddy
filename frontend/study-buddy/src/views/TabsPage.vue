<template>
  <ion-page>
    <ion-tabs>
      <ion-router-outlet></ion-router-outlet>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="assignment-list" href="/tabs/assignment-list">
          <ion-icon aria-hidden="true" :icon="documentTextOutline" />
          <ion-label>Assignments</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="course-list" href="/tabs/course-list">
          <ion-icon aria-hidden="true" :icon="folder" />
          <ion-label>Courses</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="user" href="/tabs/user">
          <ion-icon aria-hidden="true" :icon="personOutline" />
          <ion-label>Account</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="info" href="/tabs/info">
          <ion-icon aria-hidden="true" :icon="help"></ion-icon>
          <ion-label>Info</ion-label>
        </ion-tab-button>
      </ion-tab-bar>
    </ion-tabs>
  </ion-page>
</template>

<script setup lang="ts">
import { IonTabBar, IonTabButton, IonTabs, IonLabel, IonIcon, IonPage, IonRouterOutlet } from '@ionic/vue';
import { folder, personOutline, documentTextOutline , help } from 'ionicons/icons';
</script>
