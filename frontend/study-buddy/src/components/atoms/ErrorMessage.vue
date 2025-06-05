<template>
  <div v-if="show" class="error-container">
    <ion-card class="error-card">
      <ion-card-content>
        <div class="error-content">
          <ion-icon :icon="alertCircleOutline" color="danger" />
          <div class="error-text">
            <h3>Error</h3>
            <p>{{ message }}</p>
          </div>
        </div>
        <ion-button fill="clear" color="medium" @click="close">
          Dismiss
        </ion-button>
      </ion-card-content>
    </ion-card>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonCard, IonCardContent, IonButton, IonIcon } from "@ionic/vue";
import { alertCircleOutline } from "ionicons/icons";

export default defineComponent({
  components: { IonCard, IonCardContent, IonButton, IonIcon },
  props: {
    message: { type: String, required: true },
  },
  data() {
    return {
      show: true,
      alertCircleOutline,
    };
  },
  methods: {
    close() {
      this.show = false;
      this.$emit("dismiss");
    },
  },
  emits: ["dismiss"],
});
</script>

<style scoped>
.error-container {
  padding: 16px;
}

.error-card {
  border-left: 4px solid var(--ion-color-danger);
}

.error-content {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  margin-bottom: 16px;
}

.error-text h3 {
  margin: 0 0 8px 0;
  font-size: 16px;
  font-weight: 600;
  color: var(--ion-color-danger);
}

.error-text p {
  margin: 0;
  color: var(--ion-color-medium);
  line-height: 1.4;
}
</style>
