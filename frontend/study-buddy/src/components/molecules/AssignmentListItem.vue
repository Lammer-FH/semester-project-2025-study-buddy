<template>
  <ion-item lines="full">
    <ion-icon :icon="getIconByTitle(title)" slot="start" />
    <ion-label>
      <h2>{{ title }}</h2>
      <p>{{ deadline }}</p>
    </ion-label>
    <ion-buttons slot="end">
      <ion-button @click.stop="handleEdit" fill="clear" class="action-button">
        <ion-icon :icon="create" />
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
  globeOutline,
  eyedropOutline,
  hardwareChipOutline,
  fitnessOutline,
  create,
  trashOutline,
} from "ionicons/icons";

export default defineComponent({
  name: "AssignmentListItem",
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
    deadline: { type: String, required: true },
    icon: { type: String, default: globeOutline },
  },
  emits: ["edit", "delete"],
  data() {
    return {
      iconList: [
        globeOutline,
        eyedropOutline,
        hardwareChipOutline,
        fitnessOutline,
      ],
      create,
      trashOutline,
    };
  },
  methods: {
    getIconByTitle(title: string) {
      let charNumber = 0;
      for (let i = 0; i < title.length; i++) {
        charNumber += title.charCodeAt(i);
      }

      return this.iconList[charNumber % this.iconList.length];
    },
    handleEdit() {
      this.$emit("edit", this.id);
    },
    handleDelete() {
      this.$emit("delete", this.id);
    },
  },
});
</script>

<style scoped>
h2 {
  font-weight: bold;
  margin: 0;
}
p {
  margin: 0;
  font-size: 0.9em;
  color: var(--ion-color-medium);
}

.action-button ion-icon {
  color: var(--ion-color-dark);
  font-size: 20px;
}

.action-button[color="danger"] ion-icon {
  color: var(--ion-color-danger);
}
</style>
