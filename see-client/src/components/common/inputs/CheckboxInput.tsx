
interface InputProps{
    label?: string;
    checked: boolean;
    placeholder?: string;
    onChange: (checked: boolean) => void;
}

const CheckboxInput: React.FC<InputProps> = ({label, checked, placeholder, onChange}) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        onChange(e.target.checked);
    }
    return(
        <div>
           <label className="flex items-center text-sm font-medium text-gray-700">
            <input className="border border-gray-300 rounded text-sm text-gray-700 focus:ring-sky-950" type="checkbox" checked={checked} placeholder={placeholder} onChange={handleChange}/>
                <span className="ml-2">
                {label}
                </span>
            </label>
        </div>
    );
}

export default CheckboxInput;
