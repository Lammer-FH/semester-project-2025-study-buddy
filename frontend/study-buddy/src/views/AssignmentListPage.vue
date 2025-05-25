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
        <div v-if="loading">Loading Assignments…</div>
        <assignment-list v-else :assignments="assignments"></assignment-list>
      </div>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonList, IonItem } from '@ionic/vue';
import AssignmentList from '@/components/AssignmentList.vue';
import { getAssignments, Assignment } from '@/services/assignmentService'

export default defineComponent({
  components: {
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonList,
    IonItem,
    AssignmentList
  },
  data() {
    return {
      assignments: [] as Assignment[],
      loading: true

    }
  },
  mounted() {
    getAssignments().then(data => {
      this.assignments = data
      this.loading = false
    })
  }
})
</script>
