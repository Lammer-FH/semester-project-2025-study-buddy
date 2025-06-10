<template>
  <base-layout
    page-title="Assignment"
    :show-back-button="true"
    :default-back-link="`tabs/assignment-list`"
  >
    <template #header-buttons>
      <ion-buttons slot="end">
        <ion-button fill="outline" color="primary" @click="openAddTaskModal">
          <ion-icon :icon="add" slot="start" />
          Add Task
        </ion-button>
      </ion-buttons>
    </template>
    <app-spinner v-if="loading" class="loading-spinner"></app-spinner>
    <div v-if="assignmentStore.currentAssignment" class="assignment-content">
      <div class="assignment-header">
        <div class="assignment-title-wrapper">
          <h2 class="assignment-title">
            <ion-icon :icon="listOutline" class="assignment-icon" />
            {{ assignmentStore.currentAssignment.title }}
          </h2>
        </div>
        <div
          v-if="assignmentStore.currentAssignment.description"
          class="assignment-description-wrapper"
        >
          <p class="assignment-description">
            {{ assignmentStore.currentAssignment.description }}
          </p>
          <div class="assignment-meta">
            <ion-chip
              v-if="assignmentStore.currentAssignment.deadline"
              color="primary"
              outline
            >
              <ion-icon :icon="calendarOutline" />
              <ion-label
                >Due:
                {{
                  formatDate(assignmentStore.currentAssignment.deadline)
                }}</ion-label
              >
            </ion-chip>
            <ion-chip :color="getStatusColor()" outline>
              <ion-icon :icon="getStatusIcon()" />
              <ion-label>{{ getStatusText() }}</ion-label>
            </ion-chip>
          </div>
        </div>
      </div>
    </div>

    <!-- Tasks Section -->
    <div class="assignments-section">
      <div class="section-header">
        <h3 class="section-title">Tasks</h3>
        <div class="section-divider"></div>
      </div>

      <div class="assignments-content">
        <task-list
          v-if="tasks.length > 0"
          :tasks="tasks"
          @update-task="handleEdit"
          @delete-task="confirmDelete"
          class="assignment-list"
        />
        <div v-else-if="!loading" class="empty-state">
          <div class="empty-state-content">
            <ion-icon :icon="terminalOutline" class="empty-icon"></ion-icon>
            <p class="empty-message">No tasks found for this assignment.</p>
            <p class="empty-submessage">
              Get started by creating your first task.
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- Add Task Modal -->
    <ion-modal
      ref="addTaskModal"
      :is-open="isAddTaskModalOpen"
      @didDismiss="closeAddTaskModal"
    >
      <ion-header>
        <ion-toolbar>
          <ion-title>Add New Task</ion-title>
          <ion-buttons slot="end">
            <ion-button color="light" @click="closeAddTaskModal">
              <ion-icon :icon="closeOutline" />
            </ion-button>
          </ion-buttons>
        </ion-toolbar>
      </ion-header>

      <ion-content class="ion-padding">
        <ion-item class="task-input-item">
          <ion-label position="stacked">Task Description</ion-label>
          <ion-textarea
            v-model="newTask.description"
            placeholder="Enter task description..."
            :rows="1"
            :maxlength="500"
            fill="outline"
            class="task-textarea"
          ></ion-textarea>
        </ion-item>
      </ion-content>
      <ion-footer>
        <ion-toolbar>
          <ion-grid>
            <ion-row>
              <ion-col>
                <ion-button
                  expand="block"
                  fill="outline"
                  color="primary"
                  @click="closeAddTaskModal"
                  class="cancel-button"
                >
                  Cancel
                </ion-button>
              </ion-col>
              <ion-col>
                <ion-button
                  expand="block"
                  color="primary"
                  @click="createTask"
                  :disabled="!newTask.description.trim()"
                  class="create-button"
                >
                  <ion-icon :icon="checkmarkOutline" slot="start" />
                  Create Task
                </ion-button>
              </ion-col>
            </ion-row>
          </ion-grid>
        </ion-toolbar>
      </ion-footer>
    </ion-modal>
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useAssignmentStore } from "@/stores/assignmentStore";
import { useTaskStore } from "@/stores/taskStore";
import AppSpinner from "@/components/atoms/AppSpinner.vue";
import TaskList from "@/components/organisms/TaskList.vue";
import {
  listOutline,
  terminalOutline,
  add,
  closeOutline,
  checkmarkOutline,
  calendarOutline,
  checkmarkCircleOutline,
  timeOutline,
  alertCircleOutline,
} from "ionicons/icons";
import {
  IonButton,
  IonButtons,
  IonIcon,
  IonModal,
  IonContent,
  IonToolbar,
  IonTitle,
  IonItem,
  IonLabel,
  IonTextarea,
  IonCol,
  IonRow,
  IonGrid,
  IonHeader,
  IonFooter,
  IonChip,
} from "@ionic/vue";
import { Task } from "@/types/task";

export default defineComponent({
  name: "AssignmentPage",
  components: {
    TaskList,
    AppSpinner,
    IonButton,
    IonButtons,
    IonIcon,
    IonModal,
    IonContent,
    IonToolbar,
    IonTitle,
    IonItem,
    IonLabel,
    IonTextarea,
    IonCol,
    IonRow,
    IonGrid,
    IonHeader,
    IonFooter,
    IonChip,
  },
  data() {
    return {
      assignmentStore: useAssignmentStore(),
      taskStore: useTaskStore(),
      listOutline,
      add,
      terminalOutline,
      closeOutline,
      checkmarkOutline,
      checkmarkCircleOutline,
      timeOutline,
      calendarOutline,
      alertCircleOutline,
      taskIdToDelete: null as number | null,
      taskDescriptionToDelete: "",
      showDialog: false,
      error: "",
      isAddTaskModalOpen: false,
      newTask: {
        description: "",
        done: false,
      },
    };
  },
  computed: {
    tasks() {
      return this.taskStore.list;
    },
    loading() {
      return this.taskStore.isLoading;
    },
    assignmentId(): number {
      return Number(this.$route.params.id);
    },
  },
  // async mounted() {
  //   console.log("mounted");
  //   await this.loadTaskData();
  // },
  async ionViewWillEnter() {
    console.log("entered");
    // await this.assignmentStore.getAssignment(this.assignmentId);
    await this.loadTaskData();
  },
  // watch: {
  //   async "$route.params.id"() {
  //     // Only load course data if we're actually on a course page
  //     if (this.$route.name === "AssignmentPage") {
  //       await this.loadTaskData();
  //     }
  //   },
  // },
  methods: {
    async loadTaskData() {
      const id = this.getValidAssignmentId();
      if (id === null) {
        console.log("No assignment id found in route params");
        this.$router.replace("/tabs/assignment-list");
        return;
      }
      console.log("load task data get assignmnet", id);
      await this.assignmentStore.getAssignment(id);
      if (this.assignmentStore.currentAssignment) {
        console.log(
          "Set current assignmment to ",
          this.assignmentStore.currentAssignment
        );
        await this.taskStore.fetchByAssignmentId(id);
      }
    },
    getValidAssignmentId(): number | null {
      const id = Number(this.$route.params.id);
      return isNaN(id) ? null : id;
    },
    async handleEdit(task: Task) {
      console.log("Editing task", task);
      await this.taskStore.update(task.id, task);
    },
    async confirmDelete(taskId: number) {
      console.log("deleting task", taskId);
      await this.taskStore.remove(taskId);
    },
    openAddTaskModal() {
      this.isAddTaskModalOpen = true;
      this.newTask.description = ""; // Reset the form
    },
    closeAddTaskModal() {
      this.isAddTaskModalOpen = false;
      this.newTask.description = ""; // Reset the form
    },
    async createTask() {
      if (!this.newTask.description.trim()) {
        return;
      }

      try {
        await this.taskStore.create(this.assignmentId, this.newTask);
        console.log(
          "Creating task with description:",
          this.newTask.description.trim()
        );
        console.log("For assignment ID:", this.assignmentId);

        // Close modal and reset form
        this.closeAddTaskModal();

        // Refresh the task list
        await this.loadTaskData();

        // You could also show a success toast here
      } catch (error) {
        console.error("Error creating task:", error);
        this.error = "Failed to create task. Please try again.";
      }
    },
    formatDate(dateString: string): string {
      const date = new Date(dateString);
      return date.toLocaleDateString("en-US", {
        month: "short",
        day: "numeric",
        year: "numeric",
      });
    },

    getStatusText(): string {
      if (!this.tasks.length) return "No tasks";
      const completed = this.tasks.filter((t: Task) => t.done).length;
      const total = this.tasks.length;
      if (completed === total) return "Complete";
      if (completed === 0) return "Not started";
      return "In progress";
    },

    getStatusColor(): string {
      const status = this.getStatusText();
      switch (status) {
        case "Complete":
          return "success";
        case "In progress":
          return "warning";
        case "Not started":
          return "medium";
        default:
          return "medium";
      }
    },

    getStatusIcon() {
      const status = this.getStatusText();
      switch (status) {
        case "Complete":
          return checkmarkCircleOutline;
        case "In progress":
          return timeOutline;
        case "Not started":
          return alertCircleOutline;
        default:
          return alertCircleOutline;
      }
    },
  },
});
</script>

<style scoped>
ion-footer ion-toolbar {
  --background: white !important;
  background: white !important;
}
.assignment-icon {
  margin-right: 12px;
  font-size: 1.2em;
  vertical-align: middle;
}
.assignment-meta {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}
.loading-spinner {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 200px;
}

.assignment-content {
  background: var(--ion-color-light);
  margin: 16px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.assignment-header {
  padding: 24px;
}

.assignment-title-wrapper {
  margin-bottom: 12px;
}

.assignment-title {
  margin: 0;
  font-size: 1.75em;
  font-weight: 600;
  line-height: 1.3;
  color: var(--ion-color-dark);
}

.assignment-description-wrapper {
  background: transparent;
  padding: 0;
  border-radius: 0;
  margin-top: 16px;
}

.assignment-description {
  margin: 0;
  font-size: 1em;
  line-height: 1.6;
  color: var(--ion-color-medium-shade);
}

.assignments-section {
  margin: 16px;
}

.section-header {
  margin-bottom: 20px;
}

.section-title {
  margin: 0 0 8px 0;
  font-size: 1.5em;
  font-weight: 700;
  color: var(--ion-color-dark);
}

.section-divider {
  height: 3px;
  background: linear-gradient(
    90deg,
    var(--ion-color-primary) 0%,
    var(--ion-color-primary-tint) 100%
  );
  border-radius: 2px;
  width: 60px;
}

.assignments-content {
  background: var(--ion-color-light);
  border-radius: 12px;
  overflow: hidden;
}

.assignment-list {
  background: transparent;
}

.empty-state {
  padding: 48px 24px;
  text-align: center;
}

.empty-state-content {
  max-width: 280px;
  margin: 0 auto;
}

.empty-icon {
  font-size: 4em;
  color: var(--ion-color-medium);
  margin-bottom: 16px;
  opacity: 0.6;
}

.empty-message {
  margin: 0 0 8px 0;
  font-size: 1.1em;
  font-weight: 600;
  color: var(--ion-color-dark);
}

.empty-submessage {
  margin: 0;
  font-size: 0.9em;
  color: var(--ion-color-medium-shade);
  line-height: 1.4;
}

/* Modal Styles */
ion-modal {
  --height: 40% !important;
  --border-radius: 16px;
  --box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1),
    0 4px 6px -4px rgb(0 0 0 / 0.1);
}

ion-modal::part(backdrop) {
  background: rgba(0, 0, 0, 0.4);
  opacity: 1;
}

ion-modal ion-toolbar {
  --background: var(--ion-color-primary);
  --color: white;
}

.modal-content {
  padding: 20px;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.task-input-item {
  margin-bottom: 24px;
  --padding-start: 0;
  --padding-end: 0;
}

.task-textarea {
  margin-top: 8px;
}

.modal-actions {
  margin-top: auto;
  padding-top: 20px;
}

.create-button {
  margin-bottom: 12px;
  --border-radius: 8px;
  font-weight: 600;
}

.cancel-button {
  --border-radius: 8px;
}

/* Modal Styles */
ion-modal {
  --height: 40%;
  --border-radius: 16px;
  --box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1),
    0 4px 6px -4px rgb(0 0 0 / 0.1);
}

ion-modal::part(backdrop) {
  background: rgba(0, 0, 0, 0.4);
  opacity: 1;
}

ion-modal ion-toolbar {
  --background: var(--ion-color-primary);
  --color: white;
}
ion-footer {
  --background: var(--ion-color-primary-contrast);
}

.modal-content {
  padding: 20px;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.task-input-item {
  margin-bottom: 24px;
  --padding-start: 0;
  --padding-end: 0;
}

.task-textarea {
  margin-top: 8px;
}

.modal-actions {
  margin-top: auto;
  padding-top: 20px;
}

.create-button {
  margin-bottom: 12px;
  --border-radius: 8px;
  font-weight: 600;
}

.cancel-button {
  --border-radius: 8px;
}

/* Dark mode support */
@media (prefers-color-scheme: dark) {
  .assignment-content {
    background: var(--ion-color-step-50);
    border-color: var(--ion-color-step-200);
  }

  .assignment-title {
    color: var(--ion-color-light);
  }

  .assignment-description {
    color: var(--ion-color-step-600);
  }

  .assignments-content {
    background: var(--ion-color-step-50);
    border-color: var(--ion-color-step-200);
  }

  .section-title {
    color: var(--ion-color-light);
  }

  .empty-message {
    color: var(--ion-color-light);
  }
}

/* Responsive design */
@media (max-width: 768px) {
  .assignment-content {
    margin: 8px;
  }

  .assignments-section {
    margin: 8px;
  }

  .assignment-header {
    padding: 20px;
  }

  .assignment-title {
    font-size: 1.5em;
  }

  ion-modal {
    --height: 70%;
  }

  .modal-content {
    padding: 16px;
  }
}
</style>
