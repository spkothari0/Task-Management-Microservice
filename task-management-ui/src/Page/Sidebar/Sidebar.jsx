import { Avatar, Button } from '@mui/material';
import React, { useState } from 'react';
import './Sidebar.css';

const menu=[
    {name:"Home",value:"Home",role:["ROLE_ADMIN","ROLE_CUSTOMER","ROLE_HELPER"]},
    {name:"COMPLETED",value:"COMPLETED",role:["ROLE_ADMIN","ROLE_CUSTOMER","ROLE_HELPER"]},
    {name:"ASSIGNED",value:"ASSIGNED",role:["ROLE_ADMIN"]},
    {name:"PENDING",value:"PENDING",role:["ROLE_ADMIN"]},
    {name:"Create New Task",value:"Home",role:["ROLE_ADMIN"]},
    {name:"Notification",value:"Notification",role:["ROLE_CUSTOMER","ROLE_HELPER"]}
]

const role="ROLE_ADMIN";

const Sidebar = () => {
    const [activeMenu, setActiveMenu] = useState("Home");
    const handleMenuChange=(item)=>{
        setActiveMenu(item.name);
    }

    const handleLogout=()=>{
        console.log("handle Logout");
    }
    return (
        <div className=" card min-h-[85vh] flex flex-col justify-center fixed w-[20vw]">
            <div className='space-y-3 h-full'>
                <div className='flex justify-center'>
                    <Avatar sx={{width:"8rem", height:"8rem", border:'2px #c24dd0 solid'}}
                     src='https://avatars.githubusercontent.com/u/76844907?v=4'/>
                </div>
                {
                    menu.filter((item)=>item.role.includes(role)).map((item)=>(
                        <p onClick={()=>handleMenuChange(item)} className={`py-3 px-5 rounded-full text-center 
                        corsor-pointer ${activeMenu===item.name?"activeMenuItem":"menuItem"}`}>
                            {item.name}
                        </p>
                    ))
                }
                <Button className='logoutButton' fullWidth variant='contained' sx={{padding:'.7rem', borderRadius:'2rem'}}>
                    Logout
                </Button>
            </div>
        </div>
    );
}

export default Sidebar;