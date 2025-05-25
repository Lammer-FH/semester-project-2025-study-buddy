<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Assignment List</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Assignment List</ion-title>
        </ion-toolbar>
      </ion-header>
      <div class="ion-padding">
        <ion-list>
          <ion-item>Assignment 1</ion-item>
          <ion-item>Assignment 2</ion-item>
          <ion-item>Assignment 3</ion-item>
        </ion-list>
      </div>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonList, IonItem } from '@ionic/vue';

export default defineComponent({
  components: {
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonList,
    IonItem,
  },
  data() {
    return {
     
    }
  }
})
</script>
