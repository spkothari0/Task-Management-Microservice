import * as React from 'react';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import SubmissionCard from './TaskCard/SubmissionCard';
import { useDispatch, useSelector } from 'react-redux';
import { fetchSubmissionsByTaskId } from '../../ReduxToolkit/Slices/SubmissionSlice';
import { useLocation } from 'react-router-dom';
import { useEffect } from 'react';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 450,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

export default function SubmissionList({ taskId, handleClose, open }) {

    const dispatch = useDispatch();
    const { submission } = useSelector(store => store);

    useEffect(() => {
        console.log("Task ID", taskId);
        if (taskId)
            dispatch(fetchSubmissionsByTaskId(taskId));
    }, [taskId]);

    return (
        <div>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <div>
                        {submission.submissions.length > 0 ?
                        <div className='space-y-2'>
                            {submission.submissions.map((item)=><SubmissionCard item={item} />)}
                        </div>
                         :
                            <div className=''>
                                <div className='text-center'>
                                    No Submission found
                                </div>
                            </div>
                        }
                    </div>
                </Box>
            </Modal>
        </div>
    );
}
