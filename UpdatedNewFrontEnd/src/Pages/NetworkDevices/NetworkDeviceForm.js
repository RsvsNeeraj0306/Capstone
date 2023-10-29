import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './NetworkDevice.css'

const NetworkDeviceForm = () => {
    const [networkDeviceData, setNetworkDeviceData] = useState({
        manufacturer: {
            name: '', // Updated to store the selected manufacturer's name
            fieldOfWork: 'Hardware',
        },
        networkDevice: {
            hardwareName: '',
            purchaseDate: '',
            warrantyEndDate: '',
            serialNumber: '',
            quantity: '',
            cost: ''
        },
    });

    const [manufacturers, setManufacturers] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/allManufacture',{headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
          }})
            .then((response) => response.json())
            .then((data) => setManufacturers(data))
            .catch((error) => console.error('Error fetching manufacturers: ', error));
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'manufacturer.name') {
            setNetworkDeviceData({
                ...networkDeviceData,
                manufacturer: {
                    name: value,
                    fieldOfWork: networkDeviceData.manufacturer.fieldOfWork,
                },
            });
        } else {
            setNetworkDeviceData({
                ...networkDeviceData,
                [name]: value,
            });
        }
    };

    const isSubmitDisabled = () => {
        // Check if any of the required fields are empty
        return (
            !networkDeviceData.manufacturer.name ||
            !networkDeviceData.hardwareName ||
            !networkDeviceData.purchaseDate ||
            !networkDeviceData.warrantyEndDate ||
            !networkDeviceData.serialNumber ||
            !networkDeviceData.quantity ||
            !networkDeviceData.cost
        );
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('handleSubmit called'); // Add this line
    
        if (isSubmitDisabled()) {
            return;
        }
    

        const networkDeviceDTO = {
            networkDevice: {
                hardwareName: networkDeviceData.hardwareName,
                purchaseDate: networkDeviceData.purchaseDate,
                warrantyEndDate: networkDeviceData.warrantyEndDate,
                serialNumber: networkDeviceData.serialNumber,
                quantity: networkDeviceData.quantity,
                cost: networkDeviceData.cost
            },
            manufacturer: {
                name: networkDeviceData.manufacturer.name,
                fieldOfWork: networkDeviceData.manufacturer.fieldOfWork,
            }


        };

        fetch('http://localhost:8080/api/addNetworkDevices', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(networkDeviceDTO),
          })          
            .then((response) => response.json())
            .then((data) => {
                setNetworkDeviceData({
                    manufacturer: {
                        name: '',
                        fieldOfWork: 'Hardware',
                    },
                    hardwareName: '',
                    purchaseDate: '',
                    warrantyEndDate: '',
                    serialNumber: '',
                    quantity: '',
                    cost: '',
                    priceOfHardware: '',
                });
                console.log('Network Device added successfully:', data);
                toast.success('Network Device added successfully');
            })
            .catch((error) => {
                console.error('Error adding Network Device:', error);
                toast.error('An error occurred while adding Network Device');
            });
        console.log(networkDeviceDTO);
    };

    return (
        <div className="form-container" style={{'top': '100%'}}>
            <h2 className="form-label">Add Network Device</h2>
            <form onSubmit={handleSubmit}>
                <label className='form-label'>Manufacturer Name:
                    <select
                        name="manufacturer.name"
                        value={networkDeviceData.manufacturer.name}
                        onChange={handleChange}
                        className='form-input'
                    >
                        <option value="">Select a manufacturer</option>
                        {manufacturers
                            .filter((manufacturer) => manufacturer.fieldOfWork === 'Hardware')
                            .map((manufacturer) => (
                                <option key={manufacturer.id} value={manufacturer.name}>
                                    {manufacturer.name}
                                </option>
                            ))}
                    </select>
                    <Link to="/ManufacturerForm">New Manufacturer ?</Link>
                </label>
                <label className='form-label'>Hardware Name:
                    <input
                        type="text"
                        name="hardwareName"
                        value={networkDeviceData.hardwareName}
                        onChange={handleChange}
                        className='form-input'
                    />
                </label>
                <label className='form-label'>Purchase Date:
                    <input
                        type="date"
                        name="purchaseDate"
                        value={networkDeviceData.purchaseDate}
                        onChange={handleChange}
                        className='form-input'
                    />
                </label>
                <label className='form-label'>Warranty End Date:
                    <input
                        type="date"
                        name="warrantyEndDate"
                        value={networkDeviceData.warrantyEndDate}
                        onChange={handleChange}
                        className='form-input'
                    />
                </label>
                <label className='form-label'>Hardware Name Serial Number:
                    <input
                        type="text"
                        name="serialNumber"
                        value={networkDeviceData.serialNumber}
                        onChange={handleChange}
                        className='form-input'
                    />
                </label>
                <label className='form-label'>Quantity:
                    <input
                        type="text"
                        name="quantity"
                        value={networkDeviceData.quantity}
                        onChange={handleChange}
                        className='form-input'
                    />
                </label>
                <label className='form-label'>Cost:
                    <input
                        type="text"
                        name="cost"
                        value={networkDeviceData.cost}
                        onChange={handleChange}
                        className='form-input'
                    />
                </label>
                <div className="form-buttons-container">
                    <button type="submit" disabled={isSubmitDisabled()} className="form-button">Add Network Device</button>


                   
                </div>
            </form>
        </div>
    );
};

export default NetworkDeviceForm;