<?php

namespace AppBundle\Form;

use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class AlbumPhotoType extends AbstractType
{
    /**
     * @param FormBuilderInterface $builder
     * @param array $options
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('albumId', EntityType::class, array(
                'class' => 'AppBundle\Entity\Album',
                'choice_label' => 'nom',
                'choice_value' =>'id',
                'choices_as_values' => true,
            ))
            ->add('imageId', EntityType::class, array(
                'class' => 'AppBundle\Entity\Image',
                'choice_label' => 'nom',
                'choice_value' =>'id',
                'choices_as_values' => true
            ))
            ->add('orderimage')
        ;
    }
    
    /**
     * @param OptionsResolver $resolver
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'AppBundle\Entity\AlbumPhoto'
        ));
    }
}
