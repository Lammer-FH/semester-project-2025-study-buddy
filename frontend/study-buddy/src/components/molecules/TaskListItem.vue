<template>
  <ion-item class="task-item" :class="{ 'task-completed': task.done }">
    <ion-checkbox
      slot="start"
      :checked="task.done"
      @ionChange="handleToggleComplete"
      class="task-checkbox"
      color="primary"
    />

    <!-- View Mode -->
    <div v-if="!isEditing" class="task-content">
      <p class="task-text" :class="{ 'completed-text': task.done }">
        {{ task.description }}
      </p>
    </div>

    <!-- Edit Mode -->
    <div v-else class="task-edit-container">
      <ion-input
        ref="editInput"
        v-model="editText"
        @keyup.enter="handleSaveEdit"
        @keyup.escape="handleCancelEdit"
      />
      <div class="edit-actions">
        <ion-button
          @click="handleSaveEdit"
          fill="clear"
          size="small"
          color="success"
          :disabled="!editText.trim()"
        >
          <ion-icon :icon="checkmarkOutline" />
        </ion-button>
        <ion-button
          fill="clear"
          size="small"
          color="medium"
          @click="handleCancelEdit"
        >
          <ion-icon :icon="closeOutline" />
        </ion-button>
      </div>
    </div>

    <!-- Action Buttons -->
    <div slot="end" class="task-actions">
      <ion-button
        v-if="!isEditing"
        fill="clear"
        size="small"
        color="tertiary"
        @click="handleStartEdit"
      >
        <ion-icon :icon="createOutline" />
      </ion-button>
      <ion-button
        fill="clear"
        size="small"
        color="danger"
        @click="handleDelete"
      >
        <ion-icon :icon="trashOutline" />
      </ion-button>
    </div>
  </ion-item>
</template>

<script>
import { IonItem, IonCheckbox, IonInput, IonButton, IonIcon } from "@ionic/vue";
import {
  checkmarkOutline,
  closeOutline,
  createOutline,
  trashOutline,
} from "ionicons/icons";
import { showConfirmationAlert } from "@/utils/alerts";

export default {
  name: "TaskListItem",
  components: {
    IonItem,
    IonCheckbox,
    IonInput,
    IonButton,
    IonIcon,
  },
  props: {
    task: {
      type: Object,
      required: true,
    },
  },
  emits: ["update-task", "delete-task"],
  data() {
    return {
      isEditing: false,
      editText: "",
      checkmarkOutline,
      closeOutline,
      createOutline,
      trashOutline,
    };
  },
  methods: {
    handleToggleComplete() {
      const done = !this.task.done;
      console.log("Changing toggle to from ", done, this.task.done);
      this.$emit("update-task", {
        ...this.task,
        done: !this.task.done,
      });
    },
    handleStartEdit() {
      this.isEditing = true;
      this.editText = this.task.description;
      this.$nextTick(() => {
        this.$refs.editInput.$el.setFocus();
      });
    },
    handleSaveEdit() {
      if (!this.editText.trim()) return;
      console.log("edited task text to", this.editText);
      this.$emit("update-task", {
        ...this.task,
        description: this.editText.trim(),
      });
      this.isEditing = false;
    },
    handleCancelEdit() {
      this.isEditing = false;
    },
    async handleDelete() {
      await showConfirmationAlert({
        header: "Delete Task",
        message: "Are you sure you want to delete this task?",
        confirmText: "Delete",
        onConfirm: () => this.$emit("delete-task", this.task.id),
      });
    },
  },
};
</script>

<style scoped>
.task-item {
  --border-width: 0 0 1px 0;
  --border-color: var(--ion-color-light);
}

.task-completed {
  opacity: 0.6;
}

.task-content {
  flex: 1;
}

.task-text {
  margin: 0;
}

.completed-text {
  text-decoration: line-through;
  color: var(--ion-color-medium);
}

.task-edit-container,
.task-actions,
.edit-actions {
  display: flex;
  gap: 8px;
  align-items: center;
}

.task-edit-container {
  flex: 1;
}
</style>
