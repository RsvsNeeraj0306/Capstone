import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

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
        hardwareNameSerialnumber: '',
        quantity: '',
        cost: ''
        },
    });

    const [error, setError] = useState('');
    const [manufacturers, setManufacturers] = useState([]);
    const [responseMessage, setResponseMessage] = useState('');

    useEffect(() => {
        fetch('http://localhost:8080/api/allManufacture')
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
            !networkDeviceData.hardwareName ||
            !networkDeviceData.purchaseDate ||
            !networkDeviceData.warrantyEndDate ||
            !networkDeviceData.hardwareNameSerialnumber ||
            !networkDeviceData.quantity ||
            !networkDeviceData.cost
        );
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (isSubmitDisabled()) {
            setError('Please fill in all required fields before submitting.');
            return;
        }

        const networkDeviceDTO = {
            networkDevice: {
                hardwareName: networkDeviceData.hardwareName,
                purchaseDate: networkDeviceData.purchaseDate,
                warrantyEndDate: networkDeviceData.warrantyEndDate,
                serialnumber: networkDeviceData.hardwareNameSerialnumber,
                quantity: networkDeviceData.quantity,
                cost: networkDeviceData.cost
            },
            manufacturer: {
                name: networkDeviceData.manufacturer.name,
                fieldOfWork: networkDeviceData.manufacturer.fieldOfWork,
            },
            networkDeviceHistory: {
                networkDeviceName: networkDeviceData.hardwareName,
                purchaseDate: networkDeviceData.purchaseDate,
                warrantyEndDate: networkDeviceData.warrantyEndDate,
                networkDeviceHistory: networkDeviceData.networkDeviceHistory,
            }

            
        };

        fetch('http://localhost:8080/api/addNetworkDevices', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(networkDeviceDTO),
        })
            .then((response) => response.json())
            .then((data) => {
                setResponseMessage('Network Device added successfully!');
                setNetworkDeviceData({
                    manufacturer: {
                        name: '',
                        fieldOfWork: 'Hardware',
                    },
                    hardwareName: '',
                    purchaseDate: '',
                    warrantyEndDate: '',
                    hardwareNameSerialnumber: '',
                    quantity: '',
                    cost: '',
                    priceOfHardware: '',
                });
                console.log('Network Device added successfully:', data);
            })
            .catch((error) => {
                setError('Error adding Network Device: ' + error);
                console.error('Error adding Network Device:', error);
            });
    };

    return (
        <div className="form-container">
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
                    <Link to="/manufacturer">New Manufacturer ?</Link>
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
                        name="hardwareNameSerialnumber"
                        value={networkDeviceData.hardwareNameSerialnumber}
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
                    {error && <div className="form-error">{error}</div>}
                    {responseMessage && <div className="form-success">{responseMessage}</div>}
                </div>
            </form>
           
        </div>
    );
};

export default NetworkDeviceForm;