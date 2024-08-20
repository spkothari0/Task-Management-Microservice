import React from "react";
import TaskCard from "./TaskCard/TaskCard.tsx";

const TaskList = () => {
    return (
        <div className="w-[65vw]">
            <div className="space-y-3">
            {
                [1,1,1,1].map((item)=>(
                    <TaskCard/>
                ))
            }
            </div>
        </div>
    )
}

export default TaskList;