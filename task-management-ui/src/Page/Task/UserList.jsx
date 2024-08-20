import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { Avatar, Button, Divider, ListItem, ListItemAvatar, ListItemText } from '@mui/material';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  outline: 'none',
  boxShadow: 24,
  p: 2,
};

const users = [1, 1, 1, 1]

export default function UserList({ handleClose, open }) {

  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          {
            users.map((item,index) =>
              <>
                <div className='flex items-center justify-between w-full'>
                  <div>
                    <ListItem>
                      <ListItemAvatar>
                        <Avatar src='https://variety.com/wp-content/uploads/2021/04/Avatar.jpg?w=800&h=533&crop=1' />
                      </ListItemAvatar>
                      <ListItemText primary={"Shreyas Kothari Projects"} secondary={"The microservice project"} />
                    </ListItem>
                  </div>
                  <div>
                    <Button className='customButton'>Select</Button>
                  </div>
                </div>
                {index!==users.length-1 && <Divider variant='inset' />}
              </>)
          }
        </Box>
      </Modal>
    </div>
  );
}
