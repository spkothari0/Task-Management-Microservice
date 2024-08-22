import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { Autocomplete, Button, Grid, TextField } from '@mui/material';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { fetchTaskById, updateTask } from '../../../ReduxToolkit/Slices/TaskSlice';
import { useLocation } from 'react-router-dom';
import { submitTask } from '../../../ReduxToolkit/Slices/SubmissionSlice';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

export default function SubmitForm({ taskId ,handleClose, open }) {

    const dispatch = useDispatch();

    const [repoLink, setRepoLink] = React.useState('');

    const handleChange = (e) => {
        setRepoLink(e.target.value);
    }

    const handleSubmitTask = (e) => {
        e.preventDefault();
        dispatch(submitTask({taskId:taskId,repoLink:repoLink}));
        handleClose();
    }

    return (
        <div>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Typography className='text-center pb-5 text-bold' id="create-task" variant="h6" component="h2">
                        Edit Task
                    </Typography>
                    <form>
                        <Grid container spacing={2} alighItems="center">
                            <Grid item xs={12}>
                                <TextField required label="Repository Link" fullWidth name='repoLink' value={repoLink}
                                    onChange={handleChange}
                                />
                            </Grid>

                            <Grid item xs={12}>
                                <Button onClick={handleSubmitTask} className="customButton" type='submit'
                                    fullWidth variant='contained' sx={{ padding: '.9rem' }}>
                                    Submit Task
                                </Button>
                            </Grid>
                        </Grid>
                    </form>

                </Box>
            </Modal>
        </div>
    );
}
