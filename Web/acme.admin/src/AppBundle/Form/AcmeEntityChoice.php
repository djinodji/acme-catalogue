<?php
/**
 * Created by PhpStorm.
 * User: Djinodji
 * Date: 4/21/2016
 * Time: 12:15 AM
 */
namespace AppBundle\Form;
use Doctrine\ORM\EntityManager;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\ChoiceList\ArrayChoiceList;

class AcmeEntityChoice extends AbstractType
{
    protected $em;

    // Injecting EntityManager into YourType
    public function __construct(EntityManager $em)
    {
        $this->em = $em;
    }

    public function getChoiceList() {
        $array = array_combine($this->choices->nom, $this->choices->value);
        return new ArrayChoiceList($array);
    }





}
?>