import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { Autocomplete, Button, Grid, TextField } from '@mui/material';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { useDispatch } from 'react-redux';
import { createTask } from '../../../ReduxToolkit/Slices/TaskSlice';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    outline : 'none',
    boxShadow: 24,
    p: 4,
};

const allTags = ["Angular", "React", "Spring Boot", "SQL", "JavaScript", "TypeScript", "NodeJS", "ExpressJS", "MongoDB", "PostgreSQL", "MySQL", "HTML", "CSS", "SCSS", "SASS", "Bootstrap", "Material UI", "Tailwind CSS", "Selenium", "Docker", "Kubernetes", "Jenkins", "Git", "GitHub", "GitLab", "AWS", "Azure", "GCP", "Heroku", "Netlify", "Vercel", "Firebase", "OAuth", "JWT", "NestJS", "Python", "Flask", "Ruby", "Ruby on Rails", "Java", "C", "C++", "C#", "PHP", "WordPress", "Shopify"];


export default function CreateTask({ handleClose, open }) {
    const dispatch = useDispatch();

    const [formData, setFormData] = React.useState({
        title: "",
        imageURL: "",
        description: "",
        tags: [],
        deadLine: new Date()
    });

    const [selectedTags, setSelectedTags] = React.useState([]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    }

    const handleTagsChange = (e, v) => {
        setSelectedTags(v);
    }

    const handleDeadlineChange = (date) => {
        setFormData({
            ...formData,
            deadLine: date
        });
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        const { deadLine: deadline } = formData;
        const formattedDeadline = formatData(deadline);
        formData.deadLine = formattedDeadline;
        formData.tags = selectedTags;
        dispatch(createTask(formData));
        handleClose();
    }

    const formatData = (data) => {
        let {
            $y: year,
            $M: month,
            $D: day,
            $H: hour,
            $m: minute,
            $s: second,
            $ms: millisecond
        } = data;

        const date = new Date(year, month, day, hour, minute, second, millisecond);
        const formattedDate = date.toISOString();
        return formattedDate;
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
                        Create Task
                    </Typography>
                    <form>
                        <Grid container spacing={2} alighItems="center">
                            <Grid item xs={12}>
                                <TextField required label="Title" fullWidth name='title' value={formData.title}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField required label="Description" fullWidth multiline rows={4} name='description' value={formData.description}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField label="Image URL" fullWidth name='imageURL' value={formData.imageURL}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <Autocomplete
                                    multiple
                                    id="multiple-limit-tags"
                                    options={allTags}
                                    onChange={handleTagsChange}
                                    getOptionLabel={(option) => option}
                                    renderInput={(params) => (
                                        <TextField
                                            {...params}
                                            variant="filled"
                                            label="Tags"
                                        // placeholder="Tag"
                                        />
                                    )}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <LocalizationProvider dateAdapter={AdapterDayjs}>
                                    <DateTimePicker
                                        onChange={handleDeadlineChange}
                                        className='w-full' label="Deadline"
                                        renderInput={(props) => <TextField {...props} />}
                                    />
                                </LocalizationProvider>
                            </Grid>

                            <Grid item xs={12}>
                                <Button onClick={handleSubmit} className="customButton" type='submit'
                                    fullWidth variant='contained' sx={{ padding: '.9rem' }}>
                                    Create Task
                                </Button>
                            </Grid>
                        </Grid>
                    </form>

                </Box>
            </Modal>
        </div>
    );
}
