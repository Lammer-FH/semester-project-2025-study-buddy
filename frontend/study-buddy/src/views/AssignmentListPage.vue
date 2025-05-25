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
          <assignment-list-item v-for="item in assignments"
          :id="item.id"
          :title="item.title"
          :date="item.date"></assignment-list-item>
        </ion-list>
      </div>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonList, IonItem } from '@ionic/vue';
import AssignmentListItem from '@/components/AssignmentListItem.vue';

export default defineComponent({
  components: {
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonList,
    IonItem,
    AssignmentListItem
  },
  data() {
    return {
      assignments: [
        { id: 1, title: 'Assignment 1', date: '2025-05-25'},
        { id: 2, title: 'Assignment 2', date: '2025-06-01'},
        { id: 3, title: 'Assignment 3', date: '2025-06-10'}]

    }
  }
})
</script>
