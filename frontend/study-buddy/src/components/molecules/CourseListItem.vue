<template>
  <ion-item lines="full" button @click="navigateToCourse">
    <div class="icon-wrapper">
      <ion-icon :icon="currentIcon" slot="start" />
    </div>
    <ion-label>
      <h2>{{ title }}</h2>
    </ion-label>
    <ion-buttons slot="end">
      <ion-button @click.stop="handleEdit" fill="clear" class="action-button">
        <ion-icon :icon="pencilOutline" />
      </ion-button>
      <ion-button
        @click.stop="handleDelete"
        fill="clear"
        class="action-button"
        color="danger"
      >
        <ion-icon :icon="trashOutline" />
      </ion-button>
    </ion-buttons>
  </ion-item>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonItem, IonLabel, IonIcon, IonButton, IonButtons } from "@ionic/vue";
import {
  sparkles,
  server,
  rose,
  paw,
  pencilOutline,
  trashOutline,
} from "ionicons/icons";

export default defineComponent({
  name: "CourseListItem",
  components: {
    IonItem,
    IonLabel,
    IonIcon,
    IonButton,
    IonButtons,
  },
  props: {
    id: { type: Number, required: true },
    title: { type: String, required: true },
  },
  data() {
    return {
      iconList: [sparkles, server, rose, paw],
      pencilOutline,
      trashOutline,
    };
  },
  computed: {
    currentIcon() {
      let charNumber = 0;
      for (let i = 0; i < this.title.length; i++) {
        charNumber += this.title.charCodeAt(i);
      }
      return this.iconList[charNumber % this.iconList.length];
    },
  },
  methods: {
    handleEdit() {
      this.$router.push(`/tabs/course/${this.id}/edit`);
    },
    handleDelete() {
      // Emit event if needed
    },
    navigateToCourse() {
      this.$router.push(`/tabs/course/${this.id}`);
    },
  },
});
</script>

<style scoped>
h2 {
  font-weight: bold;
  margin: 0;
}

.icon-wrapper {
  background-color: var(--ion-color-primary);
  border-radius: 4px;
  padding: 8px;
  margin-right: 8px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
}

/* White icon for the course indicator */
.icon-wrapper ion-icon {
  color: white;
  font-size: 20px;
}

/* Dark icons for action buttons */
.action-button ion-icon {
  color: var(--ion-color-dark);
  font-size: 20px;
}

/* Danger color will override for delete button */
.action-button[color="danger"] ion-icon {
  color: var(--ion-color-danger);
}

ion-item {
  margin-bottom: 20px;
}
</style>
