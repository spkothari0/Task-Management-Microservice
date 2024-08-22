import React, { useState } from 'react'
import './TaskCard.css'
import { IconButton, Menu, MenuItem } from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import UserList from '../UserList';
import SubmissionList from '../SubmissionList';
import EditTaskCard from './EditTaskCard';
import { useDispatch, useSelector } from 'react-redux';
import { deleteTask } from '../../../ReduxToolkit/Slices/TaskSlice';
import { useLocation, useNavigate } from 'react-router-dom';
import { submitTask } from '../../../ReduxToolkit/Slices/SubmissionSlice';
import SubmitForm from './SubmitForm';

const role = "ROLE_ADMIN";

export default function TaskCard({ item }) {

    const dispatch = useDispatch();
    const nevigate = useNavigate();
    const location = useLocation();

    const {auth} = useSelector((state) => state);

    const [anchorEl, setAnchorEl] = useState(null);
    const openMenu = Boolean(anchorEl);

    const handleMenuClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleMenuClose = () => {
        setAnchorEl(null);
    };


    const [openUserList, setOpenUserList] = React.useState(false);
    const handleCloseUserList = () => {
        handleMenuClose();
        setOpenUserList(false);
    }
    const handleOpenUserList = () => {
        setOpenUserList(true);
        handleMenuClose();
    }

    const [openSubmissionList, setOpenSubmissionList] = React.useState(false);
    const handleCloseSubmissionList = () => {
        handleMenuClose();
        setOpenSubmissionList(false);
    }
    const handleOpenSubmissionList = () => {
        setOpenSubmissionList(true);
        handleMenuClose();
    }


    const [openUpdateTask, setOpenUpdateTask] = React.useState(false);
    const handleCloseUpdateTask = () => {
        handleMenuClose();
        setOpenUpdateTask(false);
        const updatedParams = new URLSearchParams(location.search);
        updatedParams.delete("taskId");
        const query = updatedParams.toString();
        const updatedUrl = query ? `${location.pathname}?${query}` : location.pathname;
        nevigate(updatedUrl);
    }
    const handleOpenUpdateTask = () => {
        setOpenUpdateTask(true);
        const updatedParams = new URLSearchParams(location.search);
        updatedParams.set("taskId", item.id);
        const query = updatedParams.toString();
        const updatedUrl = query ? `${location.pathname}?${query}` : location.pathname;
        nevigate(updatedUrl);
        handleMenuClose();
    }

    const handleDeleteTask = () => {
        dispatch(deleteTask(item.id));
        handleMenuClose();
    }

    const [openSubmitForm, setOpenSubmitForm] = React.useState(false);
    const handleCloseSubmitForm = () => {
        handleMenuClose();
        setOpenSubmitForm(false);
    }
    const handleOpenSubmitForm = () => {
        setOpenSubmitForm(true);
        handleMenuClose();
    }

    return (
        <div>
            <div className="card lg:flex justify-content">
                <div className="lg:flex gap-5 item-center space-y-2 w-[95%] lg:h-[70%]">
                    <div>
                        <img src={item.imageURL ? item.imageURL : "https://via.placeholder.com/150"} alt="task"
                            className="lg:w-[7rem] lg:h-[7rem] object-cover" />

                    </div>
                    <div className="space-y-5">
                        <div className="space-y-2">
                            <h1 className="font-bold text-lg">{item.title}</h1>
                            <p className="text-gray-500 text-sm">{item.description}</p>
                        </div>
                        <div className="flex flex-wrap gap-2 item-center">
                            {item.tags.map((tag) =>
                                <span className="py-1 px-5 rounded-full tags">
                                    {tag}
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
                        {auth.user?.role === "ROLE_ADMIN" ? (
                            <>
                                <MenuItem onClick={handleOpenUserList}>Assign User</MenuItem>
                                <MenuItem onClick={handleOpenSubmissionList}>View Submissions</MenuItem>
                                <MenuItem onClick={handleOpenUpdateTask}>Edit</MenuItem>
                                <MenuItem onClick={handleDeleteTask}>Delete</MenuItem>
                            </>
                        ) : (
                            <>
                                <MenuItem onClick={handleOpenSubmitForm}>Submit</MenuItem>
                            </>
                        )}
                    </Menu>
                </div>
            </div>
            <UserList taskId={item.id} handleClose={handleCloseUserList} open={openUserList} />
            <SubmissionList taskId={item.id} handleClose={handleCloseSubmissionList} open={openSubmissionList} />
            <EditTaskCard item={item} handleClose={handleCloseUpdateTask} open={openUpdateTask} />
            <SubmitForm taskId={item.id} handleClose={handleCloseSubmitForm} open={openSubmitForm} />
        </div>
    )
}