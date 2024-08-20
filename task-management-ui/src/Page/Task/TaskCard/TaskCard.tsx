import React from 'react'
import './TaskCard.css'
import { IconButton, Menu, MenuItem } from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import UserList from '../UserList';
import SubmissionList from '../SubmissionList';
import EditTaskCard from './EditTaskCard';

const role="ROLE_ADMIN";

export default function TaskCard() {
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const openMenu = Boolean(anchorEl);
    const handleMenuClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };
    const handleMenuClose = () => {
        setAnchorEl(null);
    };


    const [openUserList, setOpenUserList] = React.useState(false);
    const handleCloseUserList = () => {
        handleMenuClose();
        setOpenUserList(false);
        console.log("Close UserList");
    }
    const handleOpenUserList = () => {
        setOpenUserList(true);
        handleMenuClose();
        console.log("Open User List");
    }

    const [openSubmissionList, setOpenSubmissionList] = React.useState(false);
    const handleCloseSubmissionList = () => {
        handleMenuClose();
        setOpenSubmissionList(false);
        console.log("Close SubmissionList");
    }
    const handleOpenSubmissionList = () => {
        setOpenSubmissionList(true);
        handleMenuClose();
    }
    

    const [openUpdateTask, setOpenUpdateTask] = React.useState(false);
    const handleCloseUpdateTask = () => {
        handleMenuClose();
        setOpenUpdateTask(false);
    }
    const handleOpenUpdateTask = () => {
        setOpenUpdateTask(true);
        handleMenuClose();
    }

    const handleDeleteTask = () => {
        handleMenuClose();
        // console.log("Delete Task");
    }

    return (
        <div>
            <div className="card lg:flex justify-content">
                <div className="lg:flex gap-5 item-center space-y-2 w-[95%] lg:h-[70%]">
                    <div>
                        <img src="https://via.placeholder.com/150" alt="task" 
                        className="lg:w-[7rem] lg:h-[7rem] object-cover" />

                    </div>
                    <div className="space-y-5">
                        <div className="space-y-2">
                            <h1 className="font-bold text-lg">Car rental website</h1>
                            <p className="text-gray-500 text-sm">Use latest car model and tech to make the website and the car creatoin as best</p>
                        </div>
                        <div className="flex flex-wrap gap-2 item-center">
                            {[1,1,1,1].map((item)=>
                            <span className="py-1 px-5 rounded-full tags">
                                Angular
                            </span>
                            )}
                        </div>
                    </div>
                </div>

                <div>
                    <IconButton
                        id="basic-button"
                        aria-controls={openMenu ? 'basic-menu' : undefined}
                        aria-haspopup="true"
                        aria-expanded={openMenu ? 'true' : undefined}
                        onClick={handleMenuClick}
                    >
                        <MoreVertIcon />
                    </IconButton>

                    <Menu
                        id="basic-menu"
                        anchorEl={anchorEl}
                        open={openMenu}
                        onClose={handleMenuClose}
                        MenuListProps={{
                        'aria-labelledby': 'basic-button',
                        }}
                    >
                        {role === "ROLE_ADMIN" ?(
                            <>
                                <MenuItem onClick={handleOpenUserList}>Assign User</MenuItem>
                                <MenuItem onClick={handleOpenSubmissionList}>View Submissions</MenuItem>
                                <MenuItem onClick={handleOpenUpdateTask}>Edit</MenuItem>
                                <MenuItem onClick={handleDeleteTask}>Delete</MenuItem>
                            </>
                        ):(
                            <>
                            
                            </>
                        )}
                    </Menu>
                </div>
            </div>
            <UserList handleClose={handleCloseUserList} open={openUserList} />
            <SubmissionList handleClose={handleCloseSubmissionList} open={openSubmissionList} />
            <EditTaskCard handleClose={handleCloseUpdateTask} open={openUpdateTask} />
        </div>
    )
}