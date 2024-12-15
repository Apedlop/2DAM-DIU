import { createContext, useState } from "react";

const TaskContext = createContext();

function TaskProviderWrapper(props) {
  const [tasks, setTasks] = useState([]);
  const [hasLoaded, setLoaded] = useState(false);
  const [hasError, setError] = useState(false);

  const API_URL = "https://tasks.free.beeceptor.com";

  const getTasks = async () => {
    if (hasLoaded) return;

    try {
      console.log("get Tasks");
      const response = await fetch(API_URL);
      const data = await response.json();
      setTasks(data.reverse());
      setLoaded(true);
      setError(false);
    } catch (e) {
      setError(true);
    }
  };

  const addTask = async (newTask) => {
    try {
      await fetch(API_URL, {
        method: "POST",
        body: JSON.stringify(newTask),
      });
      setTasks([newTask, ...tasks]);
      setError(false);
    } catch (e) {
      setError(true);
    }
  };

  const updateTask = (updatedTask) => {
    const uptadedTasks = tasks.map((task) => {
      if (task.id !== updatedTask.id) return task;
      return updatedTask;
    });

    setTasks(uptadedTasks);
  };

  return (
    <TaskContext.Provider
      value={{
        tasks,
        setTasks,
        hasLoaded,
        hasError,
        getTasks,
        addTask,
        updateTask,
      }}
    >
      {props.children}
    </TaskContext.Provider>
  );
}

export { TaskContext, TaskProviderWrapper };
